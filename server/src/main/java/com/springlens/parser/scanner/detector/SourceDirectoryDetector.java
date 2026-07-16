package com.springlens.parser.scanner.detector;

import com.springlens.parser.scanner.model.ProjectLayout;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Detects the standard source directories within a project.
 */
@Component
public class SourceDirectoryDetector {

    public ProjectLayout detect(Path projectRoot) {

        ProjectLayout layout = new ProjectLayout();

        Path srcMainJava = projectRoot.resolve("src/main/java");
        Path srcTestJava = projectRoot.resolve("src/test/java");
        Path srcMainResources = projectRoot.resolve("src/main/resources");
        Path srcTestResources = projectRoot.resolve("src/test/resources");

        if (Files.isDirectory(srcMainJava)) {
            layout.setSourceDirectory(srcMainJava);
        }

        if (Files.isDirectory(srcTestJava)) {
            layout.setTestDirectory(srcTestJava);
        }

        if (Files.isDirectory(srcMainResources)) {
            layout.setResourcesDirectory(srcMainResources);
        }

        if (Files.isDirectory(srcTestResources)) {
            layout.setTestResourcesDirectory(srcTestResources);
        }

        return layout;
    }
}