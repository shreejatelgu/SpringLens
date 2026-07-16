package com.springlens.parser.scanner.service;

import com.springlens.parser.scanner.model.ProjectMetadata;

import java.nio.file.Path;

/**
 * Entry point for scanning an extracted project.
 */
public interface ProjectScanner {

    /**
     * Scans an extracted project directory and returns discovered metadata.
     *
     * @param projectRoot root directory of the extracted project
     * @return populated project metadata
     */
    ProjectMetadata scan(Path projectRoot);

}