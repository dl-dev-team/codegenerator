package com.mini.codegen;

import com.mini.codegen.config.property.CodeGenProperties;
import com.mini.codegen.service.CodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CodeGeneratorRunner implements ApplicationRunner {

    @Autowired
    @Qualifier("daoGenerator")
    private CodeGenerator daoGenerator;

    @Autowired
    @Qualifier("serviceGenerator")
    private CodeGenerator serviceGenerator;

    @Autowired
    @Qualifier("controllerGenerator")
    private CodeGenerator controllerGenerator;

    @Autowired
    @Qualifier("applicationGenerator")
    private CodeGenerator applicationGenerator;

    @Autowired
    @Qualifier("applicationYmlGenerator")
    private CodeGenerator applicationYmlGenerator;

    @Autowired
    private CodeGenProperties codeGenProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        serviceGenerator.gen();
        daoGenerator.gen();
        controllerGenerator.gen();
        applicationGenerator.gen();
        applicationYmlGenerator.gen();
    }
}
