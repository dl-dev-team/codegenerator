package com.mini.codegen.service.impl;

import com.mini.codegen.config.property.CodeGenMybatisProperties;
import com.mini.codegen.config.property.CodeGenProperties;
import com.mini.codegen.service.CodeGenerator;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

@Service("applicationYmlGenerator")
@Slf4j
public class ApplicationYmlGeneratorImpl implements CodeGenerator {

    @Autowired
    private CodeGenMybatisProperties codeGenMybatisProperties;

    @Autowired
    private CodeGenProperties codeGenProperties;

    @Autowired
    private Configuration configuration;

    @Override
    public void gen() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("url", codeGenMybatisProperties.getDatasource().getUrl());
        data.put("username", codeGenMybatisProperties.getDatasource().getUsername());
        data.put("password", codeGenMybatisProperties.getDatasource().getPassword());

        File controllerFile = new File(codeGenProperties.getProjectPath() +
                codeGenProperties.getResourcePath() +
                "/" +
                "Application.yml");
        if (!controllerFile.getParentFile().exists()) {
            controllerFile.getParentFile().mkdirs();
        } else {
            controllerFile.delete();
        }
        configuration.getTemplate("application.yml.ftl").process(data, new FileWriter(controllerFile));

        log.info("application.yml生成成功!");
    }

}
