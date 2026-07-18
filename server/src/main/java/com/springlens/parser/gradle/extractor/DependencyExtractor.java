package com.springlens.parser.gradle.extractor;

import com.springlens.parser.gradle.model.GradleDependency;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DependencyExtractor {

    /*
     * Supports:
     *
     * implementation("group:artifact:version")
     * implementation 'group:artifact:version'
     * api(...)
     * compileOnly(...)
     * runtimeOnly(...)
     * annotationProcessor(...)
     * testImplementation(...)
     * testRuntimeOnly(...)
     */

    private static final Pattern DEPENDENCY_PATTERN = Pattern.compile(
            "(implementation|api|compileOnly|runtimeOnly|annotationProcessor|testImplementation|testRuntimeOnly)" +
            "\\s*\\(?\\s*['\"]([^:'\"]+):([^:'\"]+):([^'\"]+)['\"]\\s*\\)?"
    );

    public List<GradleDependency> extract(String buildScript) {

        List<GradleDependency> dependencies = new ArrayList<>();

        Matcher matcher = DEPENDENCY_PATTERN.matcher(buildScript);

        while (matcher.find()) {

            dependencies.add(
                    new GradleDependency(
                            matcher.group(1),
                            matcher.group(2),
                            matcher.group(3),
                            matcher.group(4)
                    )
            );

        }

        return List.copyOf(dependencies);
    }

}