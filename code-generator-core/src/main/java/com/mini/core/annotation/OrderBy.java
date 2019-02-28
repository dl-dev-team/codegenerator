package com.mini.core.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OrderBy {

    int order() default 1;

    boolean asc() default true;

}
