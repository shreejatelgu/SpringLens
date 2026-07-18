package com.springlens.parser.gradle.model;

public record GradlePlugin(
        String id,
        String version,
        boolean applied
) {}