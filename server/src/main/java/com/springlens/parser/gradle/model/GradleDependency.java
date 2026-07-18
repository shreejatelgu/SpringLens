package com.springlens.parser.gradle.model;

public record GradleDependency(
        String configuration,
        String group,
        String artifact,
        String version
) {}
