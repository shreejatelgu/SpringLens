package com.springlens.parser.spring.util;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaField;
import com.springlens.parser.java.model.JavaMethod;

import java.util.List;

public final class SpringAnnotationUtils {

    private SpringAnnotationUtils() {
    }

    public static boolean hasAnnotation(
            JavaClass javaClass,
            String annotation
    ) {

        return javaClass.annotations()
                .stream()
                .anyMatch(a -> annotation.equals(a.name()));

    }

    public static boolean hasAnnotation(
            JavaMethod method,
            String annotation
    ) {

        return method.annotations()
                .stream()
                .anyMatch(a -> annotation.equals(a.name()));

    }

    public static boolean hasAnnotation(
            JavaField field,
            String annotation
    ) {

        return field.annotations()
                .stream()
                .anyMatch(a -> annotation.equals(a.name()));

    }

    public static boolean hasAnyAnnotation(
            JavaClass javaClass,
            List<String> annotations
    ) {

        return javaClass.annotations()
                .stream()
                .anyMatch(a -> annotations.contains(a.name()));

    }

}