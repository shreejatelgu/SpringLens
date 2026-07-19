package com.springlens.parser.spring.model;

public record SpringEventListener(

        String className,

        String packageName,

        String methodName

) {
}