package com.springlens.parser.java.model;

import java.util.List;

public record JavaClass(

        String name,

        String packageName,

        boolean isAbstract,

        boolean isFinal,

        String superClass,

        List<String> implementedInterfaces,

        List<JavaAnnotation> annotations,

        List<JavaField> fields,

        List<JavaConstructor> constructors,

        List<JavaMethod> methods,

        List<JavaMethodCall> methodCalls

) {
}