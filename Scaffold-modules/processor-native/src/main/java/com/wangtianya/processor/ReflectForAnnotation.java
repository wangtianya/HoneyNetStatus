package com.wangtianya.processor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectForAnnotation {


    public static void main(String[] args) throws NoSuchFieldException {
        for (Field field : GoogleFriends.class.getDeclaredFields()) {
            System.out.println(Modifier.isStatic(field.getModifiers()));
            if (field.isSynthetic()){

            }
        }




    }
}


class GoogleFriends {
    @DegreeOfLove
    public static final String Oracle = "Oracle";

    @DegreeOfLove(DegreeOfLove.Degree.hate)
    public static final String HTC = "HTC";


    private String sayHiString = "Hello, I am google!";
}

