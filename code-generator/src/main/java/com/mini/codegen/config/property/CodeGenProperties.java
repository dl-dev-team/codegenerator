package com.mini.codegen.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = CodeGenProperties.PREFIX)
@Data
@Component
public class CodeGenProperties {

    public static final String PREFIX = "smsf.codegen";

    private List<Resource> resources = new ArrayList<>();

    private String author = "s.c.gao";

    private String projectModulePath = "code-generator-output";

    private String moduleName = "";

    private String projectPath = System.getProperty("user.dir") + "/" + getProjectModulePath();

    private String resourcePath = "/src/main/resources";

    private String javaPath = "/src/main/java";

    private String basePackage = "com.accenture.smsf";

    private String entityPackage;

    private String mapperPackage;

    private String servicePackage;

    private String controllerPackage;

    public String getEntityPackage() {
        return getBasePackage() + "." + getModuleName() + "." + "core.entity";
    }

    public String getMapperPackage() {
        return getBasePackage() + "." + getModuleName() + "." + "core.mapper";
    }

    public String getServicePackage() {
        return getBasePackage() + "." + getModuleName() + "." + "service";
    }

    public String getControllerPackage() {
        return getBasePackage() + "." + getModuleName() + "." + "controller";
    }

    public String getBasePackagePath() {
        return getBasePackage().replace(".", "/");
    }

    public String getServicePath() {
        return getServicePackage().replace(".", "/");
    }

    public String getControllerPath() {
        return getControllerPackage().replace(".", "/");
    }

    @Data
    public static class Resource {

        /**
         * 数据库表名
         */
        private String tableName;
        /**
         * 数据库表名对应的模型名称
         */
        private String modelName;
        /**
         * 主键列名
         */
        private String primaryKeyColumn = "id";
    }
}
