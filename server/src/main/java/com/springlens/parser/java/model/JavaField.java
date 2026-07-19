package com.springlens.parser.java.model;

import java.util.List;

public record JavaField(

        String name,

        String type,

        boolean isStatic,

        boolean isFinal,

        String visibility,

        List<JavaAnnotation> annotations

) {
}