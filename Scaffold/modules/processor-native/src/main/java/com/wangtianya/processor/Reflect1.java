package com.wangtianya.processor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

@Lov00
public class Reflect1 {

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {


        Man man = new Man(12, "天涯");


        // 1. 获取所有变量
        for (Field field : Man.class.getDeclaredFields()) {
            field.setAccessible(true); // 使java声明的不可访问变量得以访问
            System.out.println(field.getName() + ": " + field.get(man).toString());
            System.out.println(field.getType());
        }

        // 2.获取所有方法
        for (Method method : Man.class.getDeclaredMethods()) {
            System.out.print("method: " + method.getName());
            method.invoke(man);
            System.out.println("\n");
        }

        // 3.获取修饰符
        for (Field field : Man.class.getDeclaredFields()) {
            System.out.println("isPrivate:" + Modifier.isPrivate(field.getModifiers()));
        }

        // 4.获取注解
        System.out.println(Man.class.getDeclaredAnnotation(Deprecated.class));
        System.out.println(Arrays.toString(Man.class.getDeclaredAnnotations()));

    }
}


class Humanbeing {
    private static String type = "哺乳动物";
}


@Deprecated
@Lov00
class Man extends Humanbeing {

    private int age;
    String name;

    Man(int age, String name) {
        this.age = age;
        this.name = name;
    }


    public void sayHi() {
        System.out.println(name + ": hello, friend");
    }

    public int jieqian() {
        return 11;
    }

}

