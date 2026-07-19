package com.springlens.parser.spring.model;

public record SpringCacheable(

        String className,

        String packageName,

        String methodName,

        String annotation

) {
}