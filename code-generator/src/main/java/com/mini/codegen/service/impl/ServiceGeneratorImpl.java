package com.mini.codegen.service.impl;

import com.mini.codegen.config.property.CodeGenProperties;
import com.mini.codegen.service.CodeGenerator;
import com.mini.codegen.utils.StringUtils;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

@Service("serviceGenerator")
@Slf4j
public class ServiceGeneratorImpl implements CodeGenerator {

    @Autowired
    private CodeGenProperties codeGenProperties;

    @Autowired
    private Configuration configuration;

    @Override
    public void gen() throws Exception {
        for (CodeGenProperties.Resource resource : codeGenProperties.getResources()) {
            Map<String, Object> data = new HashMap<>();
            data.put("author", codeGenProperties.getAuthor());
            data.put("moduleName", codeGenProperties.getModuleName());
            data.put("modelNameUpperCamel", resource.getModelName());
            data.put("modelNameLowerCamel", StringUtils.toLowerCaseFirstChar(resource.getModelName()));
            data.put("basePackage", codeGenProperties.getBasePackage());
            // 创建 Service 接口
            File serviceFile = new File(codeGenProperties.getProjectPath() +
                    codeGenProperties.getJavaPath() +
                    "/" +
                    codeGenProperties.getServicePath() +
                    "/" +
                    resource.getModelName() +
                    "Service.java");
            // 查看父级目录是否存在, 不存在则创建
            if (!serviceFile.getParentFile().exists()) {
                serviceFile.getParentFile().mkdirs();
            } else {
                serviceFile.delete();
            }
            configuration.getTemplate("service.ftl").process(data, new FileWriter(serviceFile));

            // 创建 Service 接口的实现类
            File serviceImplFile = new File(codeGenProperties.getProjectPath() +
                    codeGenProperties.getJavaPath() +
                    "/" +
                    codeGenProperties.getServicePath() +
                    "/impl/" +
                    resource.getModelName() +
                    "ServiceImpl.java");
            // 查看父级目录是否存在, 不存在则创建
            if (!serviceImplFile.getParentFile().exists()) {
                serviceImplFile.getParentFile().mkdirs();
            } else {
                serviceFile.delete();
            }
            configuration.getTemplate("service-impl.ftl").process(data, new FileWriter(serviceImplFile));
        }
        log.info("Service生成成功!");
    }

}
