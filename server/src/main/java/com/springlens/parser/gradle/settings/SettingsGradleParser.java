package com.springlens.parser.gradle.settings;

import com.springlens.parser.gradle.settings.model.GradleSettings;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SettingsGradleParser {

    private static final Pattern ROOT_PROJECT_PATTERN =
            Pattern.compile(
                    "rootProject\\.name\\s*=\\s*['\"]([^'\"]+)['\"]"
            );

    private static final Pattern INCLUDE_PATTERN =
            Pattern.compile(
                    "include\\((.*?)\\)",
                    Pattern.DOTALL
            );

    private static final Pattern MODULE_PATTERN =
            Pattern.compile(
                    "['\"]([^'\"]+)['\"]"
            );

    public GradleSettings parse(Path projectRoot) {

        Path settingsGradle = projectRoot.resolve("settings.gradle");
        Path settingsGradleKts = projectRoot.resolve("settings.gradle.kts");

        Path settingsFile = Files.exists(settingsGradleKts)
                ? settingsGradleKts
                : settingsGradle;

        if (!Files.exists(settingsFile)) {
            return new GradleSettings(null, List.of());
        }

        try {

            String content = Files.readString(settingsFile);

            String rootProjectName = extractRootProjectName(content);

            List<String> modules = extractModules(content);

            return new GradleSettings(
                    rootProjectName,
                    modules
            );

        } catch (IOException ex) {

            throw new IllegalStateException(
                    "Failed to parse settings.gradle",
                    ex
            );

        }

    }

    private String extractRootProjectName(String content) {

        Matcher matcher = ROOT_PROJECT_PATTERN.matcher(content);

        return matcher.find()
                ? matcher.group(1)
                : null;
    }

    private List<String> extractModules(String content) {

        List<String> modules = new ArrayList<>();

        Matcher includeMatcher = INCLUDE_PATTERN.matcher(content);

        while (includeMatcher.find()) {

            Matcher moduleMatcher =
                    MODULE_PATTERN.matcher(includeMatcher.group(1));

            while (moduleMatcher.find()) {
                modules.add(moduleMatcher.group(1));
            }

        }

        return List.copyOf(modules);
    }

}