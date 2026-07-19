package com.springlens.parser.spring.model;

public record SpringBean(
        String methodName,
        String returnType,
        String configurationClass,
        String packageName
) {
}