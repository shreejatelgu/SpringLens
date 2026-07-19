package com.springlens.parser.java.model;

public record JavaMethodCall(

        String callerClass,

        String callerMethod,

        String targetObject,

        String targetMethod

) {
}