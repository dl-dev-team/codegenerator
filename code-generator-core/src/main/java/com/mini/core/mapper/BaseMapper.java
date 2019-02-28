package com.mini.core.mapper;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;

/**
 * @param <T>
 * @author dawei.li
 */
public interface BaseMapper<T> extends tk.mybatis.mapper.common.BaseMapper<T>, ConditionMapper<T>, IdsMapper<T>,
        CustomInsertListMapper<T> {
}
