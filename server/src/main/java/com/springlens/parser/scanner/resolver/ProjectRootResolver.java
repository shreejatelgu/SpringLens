package com.springlens.parser.scanner.resolver;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Resolves the actual root directory of a Java project.
 */
@Component
public class ProjectRootResolver {

    /**
     * Attempts to locate the directory containing the build file.
     *
     * @param extractedDirectory extracted ZIP directory
     * @return resolved project root
     */
    public Path resolve(Path extractedDirectory) {

        try (Stream<Path> stream = Files.walk(extractedDirectory, 3)) {

            Optional<Path> buildFile = stream
                    .filter(Files::isRegularFile)
                    .filter(this::isBuildFile)
                    .min(Comparator.comparingInt(path -> path.getNameCount()));

                return buildFile
                    .flatMap(path -> Optional.ofNullable(path.getParent()))
                    .orElse(extractedDirectory);

        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Failed to resolve project root",
                    ex
            );
        }
    }

    private boolean isBuildFile(Path path) {

        String file = path.getFileName().toString();

        return file.equals("pom.xml")
                || file.equals("build.gradle")
                || file.equals("build.gradle.kts");
    }
}