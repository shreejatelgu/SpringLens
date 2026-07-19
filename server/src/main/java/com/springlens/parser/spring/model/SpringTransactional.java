package com.springlens.parser.spring.model;

public record SpringTransactional(

        String className,

        String packageName,

        String methodName

) {
}