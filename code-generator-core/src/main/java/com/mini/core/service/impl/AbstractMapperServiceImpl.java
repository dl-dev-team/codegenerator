package com.mini.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.mini.core.annotation.IsId;
import com.mini.core.annotation.OrderBy;
import com.mini.core.mapper.BaseMapper;
import com.mini.core.service.MapperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Slf4j
public abstract class AbstractMapperServiceImpl<T> implements MapperService<T> {

    @Autowired
    BaseMapper<T> baseMapper;

    private Class<T> tClass;

    public AbstractMapperServiceImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        tClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public int save(T model) {
        setValueToIdIfEmpty(model);
        return baseMapper.insertSelective(model);
    }

    private void setValueToIdIfEmpty(T model) {
        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() != String.class) {
                continue;
            }
            Id id = field.getAnnotation(Id.class);
            if (id == null) {
                continue;
            } else {
                field.setAccessible(true);
                try {
                    Object value = field.get(model);
                    if (null == value) {
                        field.set(model, UUID.randomUUID().toString());
                    }
                } catch (IllegalAccessException e) {

                }
                break;
            }
        }
    }

    @Override
    public int save(List<T> models) {
        models.forEach(model -> {
            setValueToIdIfEmpty(model);
        });
        return baseMapper.insertList(models);
    }

    @Override
    public int update(T model) {
        return baseMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public int delete(String id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int batchDelete(String ids) {
        return baseMapper.deleteByIds(ids);
    }

    @Override
    public List<T> list(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return baseMapper.selectAll();
    }

    @Override
    public List<T> list() {
        return baseMapper.selectAll();
    }

    @Override
    public T findById(String id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = tClass.getConstructor().newInstance();
            Field field = tClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return baseMapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            log.info("查询操作异常", e);
            return null;
        }
    }

    @Override
    public List<T> findBy(T t, int pageNumber, int pageSize) {
        Condition condition = generateCondition(t);
        if (null == condition) {
            return Collections.emptyList();
        }
        PageHelper.startPage(pageNumber, pageSize);
        return baseMapper.selectByCondition(condition);
    }

    private LinkedHashMap<String, Integer> mapSort(Map<String, Integer> map) {
        //1、按顺序保存map中的元素，使用LinkedList类型
        List<Map.Entry<String, Integer>> keyList = new LinkedList<>(map.entrySet());
        //2、按照自定义的规则排序
        Collections.sort(keyList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                if (o2.getValue().compareTo(o1.getValue()) > 0) {
                    return -1;
                } else if (o2.getValue().compareTo(o1.getValue()) < 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        //3、将LinkedList按照排序好的结果，存入到HashMap中
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : keyList) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private void generateOrderBy(Condition condition, Field[] fields) {
        Map<String, Integer> orderMap = new HashMap<>();
        for (Field field : fields) {
            OrderBy orderBy = field.getAnnotation(OrderBy.class);
            if (orderBy != null) {
                Column column = field.getAnnotation(Column.class);
                String columnName = null;
                if (null == column) {
                    columnName = field.getName();
                } else {
                    columnName = column.name();
                }
                if (orderBy.asc()) {
                    orderMap.put(columnName + " asc", orderBy.order());
                } else {
                    orderMap.put(columnName + " desc", orderBy.order());
                }
            }
        }
        LinkedHashMap<String, Integer> sortedOrderMap = mapSort(orderMap);
        StringBuilder stringBuilder = new StringBuilder(100);
        sortedOrderMap.forEach((key, value) -> {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(key);
        });
        if (stringBuilder.length() > 0) {
            condition.setOrderByClause(stringBuilder.toString());
        }
    }

    private Condition generateCondition(T t) {
        Condition condition = new Condition(t.getClass(), false, false);
        Example.Criteria criteria = condition.createCriteria();
        Field[] fields = t.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(t);
                if (value == null) {
                    continue;
                }
                Class<?> type = field.getType();
                if (type == int.class || type == Integer.class || type == long.class ||
                        type == Long.class || type == double.class || type == Double.class ||
                        type == float.class || type == Float.class || type == Date.class) {
                    // 数字和日期类型 == 匹配
                    criteria.andEqualTo(field.getName(), value);
                } else if (type == String.class) {
                    IsId isId = field.getAnnotation(IsId.class);
                    if (null == isId) {
                        // 字符类型模糊匹配，为了使用索引，只支持后置模糊匹配
                        criteria.andLike(field.getName(), value + "%");
                    } else {
                        // 如果是一个id列，那么直接进行值匹配
                        criteria.andEqualTo(field.getName(), value);
                    }
                } else {
                    // 其他情况暂不处理
                }
            }
            generateOrderBy(condition, fields);
        } catch (Exception e) {
            log.error("查询参数解析异常", e);
            return null;
        }
        return condition;
    }

    @Override
    public List<T> findBy(T t) {
        Condition condition = generateCondition(t);
        if (null == condition) {
            return Collections.emptyList();
        }
        return baseMapper.selectByCondition(condition);
    }

    @Override
    public List<T> findByColumns(T t, String columns) {
        Condition condition = generateCondition(t);
        if (null == condition) {
            return Collections.emptyList();
        }
        List<T> resultSet = baseMapper.selectByCondition(condition);
        Field[] fields = t.getClass().getDeclaredFields();
        List<Field> shouldEmptyFields = new ArrayList<>(fields.length);
        for (Field field : fields) {
            if (!columns.contains(field.getName())) {
                shouldEmptyFields.add(field);
            }
        }
        try {
            for (T entity : resultSet) {
                for (Field field : shouldEmptyFields) {
                    field.setAccessible(true);
                    field.set(entity, null);
                }
            }
        } catch (Exception e) {
            log.error("查询参数解析异常", e);
            return null;
        }
        return resultSet;
    }

}
