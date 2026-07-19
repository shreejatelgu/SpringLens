package com.springlens.parser.java.model;

import java.util.List;

public record JavaMethod(
        String name,
        String returnType,
        String visibility,
        boolean isStatic,
        boolean isAbstract,

        List<JavaAnnotation> annotations,
        List<JavaParameter> parameters
) {
}