package com.springlens.parser.gradle.model;

public record ProjectInfo(
        String name,
        String group,
        String version,
        String description
) {
}