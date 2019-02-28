package com.mini.codegen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CodeGenApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeGenApplication.class, args);
        log.info("============= Code generator finished ===============");
    }

}

