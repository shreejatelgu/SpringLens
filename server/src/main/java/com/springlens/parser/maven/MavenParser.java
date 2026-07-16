package com.springlens.parser.maven;

import com.springlens.parser.maven.model.MavenProjectInfo;

import java.nio.file.Path;

/**
 * Parses Maven projects.
 */
public interface MavenParser {

    /**
     * Parses a pom.xml file.
     *
     * @param projectRoot project root directory
     * @return parsed Maven information
     */
    MavenProjectInfo parse(Path projectRoot);

}