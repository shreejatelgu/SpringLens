package com.springlens.parser.gradle.resolver;

import com.springlens.parser.gradle.settings.SettingsGradleParser;
import com.springlens.parser.gradle.settings.model.GradleSettings;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
public class MultiModuleResolver {

    private final SettingsGradleParser settingsGradleParser;

    public MultiModuleResolver(
            SettingsGradleParser settingsGradleParser
    ) {
        this.settingsGradleParser = settingsGradleParser;
    }

    public List<Path> resolve(Path projectRoot) {

        GradleSettings settings =
                settingsGradleParser.parse(projectRoot);

        if (settings.rootProjectName() == null ||
                settings.includedModules().isEmpty()) {
            return List.of(projectRoot);
        }

        return settings.includedModules()
                .stream()
                .map(module -> module.replace(":", "/"))
                .map(projectRoot::resolve)
                .toList();
    }

}