package com.mini.codegen.config;

import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FreemarkerConfig {

    @Bean
    freemarker.template.Configuration initFreemarkerConfiguration() throws IOException {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(getClass(), "/templates/");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return configuration;
    }

}
