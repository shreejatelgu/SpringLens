package com.springlens.parser.java.model;

import java.util.List;

public record JavaConstructor(
        String name,
        String visibility,

        List<JavaAnnotation> annotations,
        List<JavaParameter> parameters
) {
}