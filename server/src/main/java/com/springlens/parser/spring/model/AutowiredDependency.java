package com.springlens.parser.spring.model;

public record AutowiredDependency(
        String sourceClass,
        String packageName,
        String fieldName,
        String targetType
) {
}