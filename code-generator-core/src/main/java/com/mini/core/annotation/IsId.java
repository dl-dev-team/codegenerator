package com.mini.core.annotation;


import org.graalvm.compiler.lir.CompositeValue;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsId {
}
