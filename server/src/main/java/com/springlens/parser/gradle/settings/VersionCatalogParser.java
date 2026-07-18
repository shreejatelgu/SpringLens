package com.springlens.parser.gradle.settings;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Component
public class VersionCatalogParser {

    public Map<String, String> parse(Path projectRoot) {

        Path catalog = projectRoot.resolve("gradle")
                .resolve("libs.versions.toml");

        if (!Files.exists(catalog)) {
            return Map.of();
        }

        try {

            Map<String, String> versions = new HashMap<>();

            boolean insideVersions = false;

            for (String line : Files.readAllLines(catalog)) {

                line = line.trim();

                if (line.equals("[versions]")) {
                    insideVersions = true;
                    continue;
                }

                if (insideVersions && line.startsWith("[")) {
                    break;
                }

                if (!insideVersions ||
                        line.isBlank() ||
                        line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("=", 2);

                if (parts.length != 2) {
                    continue;
                }

                versions.put(
                        parts[0].trim(),
                        parts[1]
                                .trim()
                                .replace("\"", "")
                );

            }

            return Map.copyOf(versions);

        } catch (IOException ex) {

            throw new IllegalStateException(
                    "Failed to parse Version Catalog.",
                    ex
            );

        }

    }

}