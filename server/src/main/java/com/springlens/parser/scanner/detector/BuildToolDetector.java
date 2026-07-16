package com.springlens.parser.scanner.detector;

import com.springlens.parser.scanner.model.BuildTool;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Detects the build tool used by a project.
 */
@Component
public class BuildToolDetector {

    /**
     * Detects the build tool based on build files present in the project root.
     *
     * @param projectRoot extracted project root
     * @return detected build tool
     */
    public BuildTool detect(Path projectRoot) {

        if (Files.exists(projectRoot.resolve("pom.xml"))) {
            return BuildTool.MAVEN;
        }

        if (Files.exists(projectRoot.resolve("build.gradle"))
                || Files.exists(projectRoot.resolve("build.gradle.kts"))) {
            return BuildTool.GRADLE;
        }

        return BuildTool.UNKNOWN;
    }
}