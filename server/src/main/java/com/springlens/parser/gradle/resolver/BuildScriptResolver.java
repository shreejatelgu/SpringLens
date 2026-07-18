package com.springlens.parser.gradle.resolver;

import java.nio.file.Path;

public interface BuildScriptResolver {

    /**
     * Resolves the Gradle build script for the given project root.
     *
     * @param projectRoot the root directory of the project
     * @return the resolved Gradle build script
     * @throws GradleParseException if no supported Gradle build script is found
     */
    Path resolve(Path projectRoot);

}