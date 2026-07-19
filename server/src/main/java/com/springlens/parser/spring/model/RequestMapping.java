package com.springlens.parser.spring.model;

public record RequestMapping(
        String controllerClass,
        String packageName,
        String methodName,
        String mappingType
) {
}