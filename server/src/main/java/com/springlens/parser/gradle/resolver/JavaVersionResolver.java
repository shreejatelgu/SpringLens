package com.springlens.parser.gradle.resolver;

import com.springlens.parser.gradle.model.GradleProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JavaVersionResolver {

    private static final List<String> JAVA_VERSION_KEYS = List.of(
            "java.version",
            "javaVersion",
            "sourceCompatibility",
            "targetCompatibility"
    );

    public String resolve(List<GradleProperty> properties) {

        for (String key : JAVA_VERSION_KEYS) {

            for (GradleProperty property : properties) {

                if (key.equals(property.key())) {
                    return property.value();
                }

            }

        }

        return null;
    }

}