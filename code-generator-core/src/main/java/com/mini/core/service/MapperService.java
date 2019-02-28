package com.mini.core.service;

import java.util.List;

/**
 * @param <T>
 * @author s.c.gao
 */
public interface MapperService<T> {

    /**
     * 保存一条记录
     *
     * @param model
     * @return
     */
    int save(T model);

    /**
     * 批量保存记录
     *
     * @param models
     */
    int save(List<T> models);

    /**
     * 更新指定列
     *
     * @param model
     * @return
     */
    int update(T model);

    /**
     * 删除一条记录
     *
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 删除多条记录
     *
     * @param ids "a,b,c,d"
     * @return
     */
    int batchDelete(String ids);

    /**
     * 分页查询列表
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<T> list(int pageNumber, int pageSize);

    /**
     * 无分页查询列表
     *
     * @return
     */
    List<T> list();

    /**
     * 根据一个id查询记录
     *
     * @param id
     * @return
     */
    T findById(String id);

    /**
     * 根据输入条件查询
     *
     * @param t
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<T> findBy(T t, int pageNumber, int pageSize);

    /**
     * 根据输入条件查询, 无分页
     *
     * @param t
     * @return
     */
    List<T> findBy(T t);

    /**
     * 根据输入条件查询, 无分页
     *
     * @param t
     * @return
     */
    List<T> findByColumns(T t, String columns);

    /**
     * 单条件，指定查询
     *
     * @param fieldName
     * @param value
     * @return
     */
    T findBy(String fieldName, Object value);
}
