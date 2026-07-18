package com.springlens.parser.gradle.resolver;

import com.springlens.parser.gradle.model.GradlePlugin;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringBootVersionResolver {

    private static final String SPRING_BOOT_PLUGIN =
            "org.springframework.boot";

    public String resolve(List<GradlePlugin> plugins) {

        if (plugins == null || plugins.isEmpty()) {
            return null;
        }

        for (GradlePlugin plugin : plugins) {

            if (SPRING_BOOT_PLUGIN.equals(plugin.id())) {
                return plugin.version();
            }

        }

        return null;
    }

}