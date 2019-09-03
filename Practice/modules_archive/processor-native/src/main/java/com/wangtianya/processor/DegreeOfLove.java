package com.wangtianya.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
public @interface DegreeOfLove {
    Degree value() default Degree.normal;

    enum Degree {
        normal("一般"), most("挚爱"), hate("痛恨");

        private final String text;

        Degree(final String text) {
            this.text = text;
        }
    }
}
