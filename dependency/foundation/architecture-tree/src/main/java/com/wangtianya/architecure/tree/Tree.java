package com.wangtianya.architecure.tree;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Tree {

    private static Application application = null;

    public static void init(Application application) {
        Tree.application = application;
    }

    public static Application getContext() {
        return application;
    }


    @SuppressWarnings("unchecked")
    static void autoCreateLeaf(LifecycleOwner target) { // 自动创建、注入Component、自动bindLifecycle
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
            if (Leaf.class.equals(field.getType().getSuperclass())) {
                try {
                    Leaf presenter = (Leaf) field.getType().getConstructor().newInstance();
                    presenter.setComponent(target);
                    target.getLifecycle().addObserver(presenter);
                    field.set(target, presenter);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static void autoCreateBinding(LifecycleOwner target, Context context) { // 自动创建、注入Component、自动bindLifecycle
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
}
