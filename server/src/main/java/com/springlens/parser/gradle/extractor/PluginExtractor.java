package com.springlens.parser.gradle.extractor;

import com.springlens.parser.gradle.model.GradlePlugin;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PluginExtractor {

    /*
     * Supports:
     *
     * plugins {
     *     id("org.springframework.boot")
     *     id("io.spring.dependency-management") version "1.1.7"
     * }
     *
     * plugins {
     *     id 'org.springframework.boot'
     *     id 'io.spring.dependency-management' version '1.1.7'
     * }
     */

    private static final Pattern KOTLIN_PLUGIN_PATTERN = Pattern.compile(
            "id\\(\"([^\"]+)\"\\)\\s*(?:version\\s*\"([^\"]+)\")?"
    );

    private static final Pattern GROOVY_PLUGIN_PATTERN = Pattern.compile(
            "id\\s+'([^']+)'\\s*(?:version\\s+'([^']+)')?"
    );

    public List<GradlePlugin> extract(String buildScript) {

        List<GradlePlugin> plugins = new ArrayList<>();

        extract(buildScript, KOTLIN_PLUGIN_PATTERN, plugins);
        extract(buildScript, GROOVY_PLUGIN_PATTERN, plugins);

        return List.copyOf(plugins);
    }

    private void extract(
            String buildScript,
            Pattern pattern,
            List<GradlePlugin> plugins
    ) {

        Matcher matcher = pattern.matcher(buildScript);

        while (matcher.find()) {

            plugins.add(
                    new GradlePlugin(
                            matcher.group(1),
                            matcher.group(2),
                            true
                    )
            );
        }
    }

}