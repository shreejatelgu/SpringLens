package com.springlens.parser.gradle.settings.model;

import java.util.List;

public record GradleSettings(
        String rootProjectName,
        List<String> includedModules
) {

    public GradleSettings {
        includedModules = includedModules == null
                ? List.of()
                : List.copyOf(includedModules);
    }

}