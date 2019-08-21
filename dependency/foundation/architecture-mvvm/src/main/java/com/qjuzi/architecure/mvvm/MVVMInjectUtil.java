/*
 * Copyright (C) 2019 Godya. All Rights Reserved.
 */
package com.qjuzi.architecure.mvvm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;

public class MVVMInjectUtil {

    public static void autoCreatePresenter(LifecycleOwner target) { // 自动创建、注入Component、自动bindLifecycle
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.get(target) != null) {
                    continue;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (MVVMPresenter.class.equals(field.getType().getSuperclass())) {
                try {
                    MVVMPresenter presenter = (MVVMPresenter) field.getType().getConstructor().newInstance();
                    presenter.setComponent(target);
                    target.getLifecycle().addObserver(presenter);
                    field.set(target, presenter);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void autoCreateModel(LifecycleOwner target) { // 自动创建、注入Component、自动bindLifecycle
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.get(target) != null) {
                    continue;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (MVVMModel.class.equals(field.getType().getSuperclass())) {
                try { // TODO import viewmodelprovider to create it
                    MVVMModel model = (MVVMModel) field.getType().getConstructor().newInstance();
                    model.setComponent(target);
                    target.getLifecycle().addObserver(model);
                    field.set(target, model);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void autoCreateBinding(LifecycleOwner target, Context context) { // 自动创建、注入Component、自动bindLifecycle
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.get(target) != null) {
                    continue;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (android.databinding.ViewDataBinding.class.equals(field.getType().getSuperclass())) {
                try {
                    Method method = field.getType().getMethod("inflate", android.view.LayoutInflater.class);
                    ViewDataBinding obj = (ViewDataBinding) method.invoke(null, LayoutInflater.from(context));
                    obj.setLifecycleOwner(target);
                    field.set(target, obj);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void notifyViewEvent(Object target, Class annotationClass) {
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!MVVMPresenter.class.equals(field.getType().getSuperclass())
                    && !MVVMModel.class.equals(field.getType().getSuperclass())) {
                continue;
            }
            try {
                notifyViewEvent(annotationClass, field.getType().getDeclaredMethods(), field.get(target));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        notifyViewEvent(annotationClass, target.getClass().getDeclaredMethods(), target);
    }

    private static void notifyViewEvent(Class annotationClass, Method[] methods, Object target) {
        for (Method method : methods) {
            method.setAccessible(true);
            if (method.getAnnotation(annotationClass) == null) {
                continue;
            }
            try {
                method.invoke(target);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
