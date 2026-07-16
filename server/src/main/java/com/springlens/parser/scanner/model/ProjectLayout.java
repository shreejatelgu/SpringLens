package com.springlens.parser.scanner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.file.Path;

/**
 * Represents the directory layout of a project.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectLayout {

    /**
     * src/main/java
     */
    private Path sourceDirectory;

    /**
     * src/test/java
     */
    private Path testDirectory;

    /**
     * src/main/resources
     */
    private Path resourcesDirectory;

    /**
     * src/test/resources
     */
    private Path testResourcesDirectory;

}