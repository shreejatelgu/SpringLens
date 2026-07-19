package com.springlens.parser.java.model;

import java.util.List;
public record JavaProject(

        List<String> packages,

        List<String> imports,

        List<JavaClass> classes,

        List<JavaInterface> interfaces,

        List<JavaEnum> enums,

        List<JavaRecord> records

) {
}