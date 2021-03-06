package com.asynchrony.webdriver.wordpress.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WordpressLogin
{
    String username();
    String password();
    String url() default "";
    String urlVariable() default "";
}
