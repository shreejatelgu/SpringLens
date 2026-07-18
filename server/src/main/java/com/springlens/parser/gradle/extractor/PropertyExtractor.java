package com.springlens.parser.gradle.extractor;

import com.springlens.parser.gradle.model.GradleProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PropertyExtractor {

    /*
     * Supports:
     *
     * extra["javaVersion"] = "21"
     * extra["springBootVersion"] = "3.5.4"
     *
     * ext {
     *     javaVersion = "21"
     *     springBootVersion = "3.5.4"
     * }
     */

    private static final Pattern EXTRA_PATTERN = Pattern.compile(
            "extra\\[\"([^\"]+)\"\\]\\s*=\\s*[\"']([^\"']+)[\"']"
    );

    private static final Pattern EXT_BLOCK_PATTERN = Pattern.compile(
            "ext\\s*\\{(.*?)\\}",
            Pattern.DOTALL
    );

    private static final Pattern EXT_PROPERTY_PATTERN = Pattern.compile(
            "([A-Za-z0-9_.-]+)\\s*=\\s*[\"']([^\"']+)[\"']"
    );

    public List<GradleProperty> extract(String buildScript) {

        List<GradleProperty> properties = new ArrayList<>();

        extractExtraProperties(buildScript, properties);
        extractExtBlockProperties(buildScript, properties);

        return List.copyOf(properties);
    }

    private void extractExtraProperties(
            String buildScript,
            List<GradleProperty> properties
    ) {

        Matcher matcher = EXTRA_PATTERN.matcher(buildScript);

        while (matcher.find()) {

            properties.add(
                    new GradleProperty(
                            matcher.group(1),
                            matcher.group(2)
                    )
            );
        }
    }

    private void extractExtBlockProperties(
            String buildScript,
            List<GradleProperty> properties
    ) {

        Matcher blockMatcher = EXT_BLOCK_PATTERN.matcher(buildScript);

        while (blockMatcher.find()) {

            String block = blockMatcher.group(1);

            Matcher propertyMatcher = EXT_PROPERTY_PATTERN.matcher(block);

            while (propertyMatcher.find()) {

                properties.add(
                        new GradleProperty(
                                propertyMatcher.group(1),
                                propertyMatcher.group(2)
                        )
                );
            }
        }
    }

}