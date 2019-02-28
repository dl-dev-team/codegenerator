package com.mini.core.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@tk.mybatis.mapper.annotation.RegisterMapper
public interface CustomInsertListMapper<T> {

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等
     *
     * @param recordList
     * @return
     */
    @Options(useGeneratedKeys = false)
    @InsertProvider(type = CustomSpecialProvider.class, method = "dynamicSQL")
    int insertList(List<? extends T> recordList);
}
