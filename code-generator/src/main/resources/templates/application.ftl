package ${basePackage}.${moduleName};

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
/**
 *
 * @author ${author}
 */
 @SpringBootApplication
 @Slf4j
public class ${moduleNameUpperCase}Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(${moduleNameUpperCase}Application.class, args);
        log.info(" ========== " + applicationContext.getId() + " started ==========");
    }

}
