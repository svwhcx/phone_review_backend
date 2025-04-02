package com.svwh.phonereview.permission.annotation;

import com.svwh.phonereview.permission.CheckMode;
import com.svwh.phonereview.permission.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckPermission {

    RoleEnum[] value() default {RoleEnum.ADMIN};

    CheckMode mode() default CheckMode.OR;

}
