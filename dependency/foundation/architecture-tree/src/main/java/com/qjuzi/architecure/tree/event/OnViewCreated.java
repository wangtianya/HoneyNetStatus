/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.qjuzi.architecure.tree.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import kotlin.Suppress;

@SuppressWarnings("unused")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Suppress(names = {"unused"})
public @interface OnViewCreated {
}
