package com.talent.six.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在需要token验证的Controller方法上使用此注解
 *
 * ElementType.METHOD   表示该自定义注解可以用在方法上
 * RetentionPolicy.RUNTIME   表示该注解在代码运行时起作用
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenRequired {}