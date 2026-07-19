package com.springlens.parser.spring.model;

public record SpringQualifier(

        String className,

        String fieldName,

        String qualifier

) {
}