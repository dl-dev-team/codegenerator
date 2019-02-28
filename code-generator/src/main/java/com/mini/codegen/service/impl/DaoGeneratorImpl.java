package com.mini.codegen.service.impl;

import com.mini.codegen.config.property.CodeGenMybatisProperties;
import com.mini.codegen.config.property.CodeGenProperties;
import com.mini.codegen.service.CodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("daoGenerator")
@Slf4j
public class DaoGeneratorImpl implements CodeGenerator {

    @Autowired
    private CodeGenProperties codeGenProperties;

    @Autowired
    private CodeGenMybatisProperties codeGenMybatisProperties;

    @Override
    public void gen() throws Exception {
        List<String> warnings = null;
        try {
            Configuration cfg = new Configuration();
            cfg.addContext(initConfig());
            cfg.validate();

            DefaultShellCallback callback = new DefaultShellCallback(true);
            warnings = new ArrayList<String>();
            MyBatisGenerator generator = new MyBatisGenerator(cfg, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            log.info("生成失败!", e);
            throw new RuntimeException("生成失败!", e);
        }
        log.info("Dao 生成成功!");
    }

    public Context initMybatisGeneratorContext() {
        Context context = new Context(ModelType.FLAT);
        // 设置context的一些属性
        context.setId(codeGenMybatisProperties.getContext().getId());
        context.setTargetRuntime(codeGenMybatisProperties.getContext().getTargetRuntime());
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
        // 设置jdbc
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(codeGenMybatisProperties.getDatasource().getUrl());
        jdbcConnectionConfiguration.setUserId(codeGenMybatisProperties.getDatasource().getUsername());
        jdbcConnectionConfiguration.setPassword(codeGenMybatisProperties.getDatasource().getPassword());
        jdbcConnectionConfiguration.setDriverClass(codeGenMybatisProperties.getDatasource().getDriverClassName());
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        // 设置 mapping xml
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(codeGenProperties.getProjectPath() + codeGenProperties.getResourcePath());
        sqlMapGeneratorConfiguration.setTargetPackage("mappings." + codeGenProperties.getModuleName());
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
        // 设置 entity
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(codeGenProperties.getProjectPath() + codeGenProperties.getJavaPath());
        javaModelGeneratorConfiguration.setTargetPackage(codeGenProperties.getEntityPackage());
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
        // 设置 mapper
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(codeGenProperties.getProjectPath() + codeGenProperties.getJavaPath());
        javaClientGeneratorConfiguration.setTargetPackage(codeGenProperties.getMapperPackage());
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        // 增加 mapper 插件
        addMapperPlugin(context);

        return context;
    }

    private void addMapperPlugin(Context context) {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", "com.ac.smsf.codegen.core.mapper.BaseMapper");
        context.addPluginConfiguration(pluginConfiguration);
    }

    private Context initConfig() {
        Context context = initMybatisGeneratorContext();
        codeGenProperties.getResources().forEach(resource -> {
            TableConfiguration tableConfiguration = new TableConfiguration(context);
            tableConfiguration.setTableName(resource.getTableName());
            tableConfiguration.setDomainObjectName(resource.getModelName());
            // 如果需要再表上加主键生成策略，需要配置下面这句话，直接指定为mysql，如果时其他数据库，也可以指定，可以参考tk的wiki
            // tableConfiguration.setGeneratedKey(new GeneratedKey(resource.getPrimaryKeyColumn(), "Mysql", true,
            // null));
            context.addTableConfiguration(tableConfiguration);
        });
        return context;
    }
}
