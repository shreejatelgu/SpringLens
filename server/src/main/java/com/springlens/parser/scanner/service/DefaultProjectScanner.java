package com.springlens.parser.scanner.service;

import com.springlens.parser.scanner.detector.BuildToolDetector;
import com.springlens.parser.scanner.detector.SourceDirectoryDetector;
import com.springlens.parser.scanner.model.ProjectMetadata;
import com.springlens.parser.scanner.model.ScanStatus;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class DefaultProjectScanner implements ProjectScanner {

    private final BuildToolDetector buildToolDetector;
    private final SourceDirectoryDetector sourceDirectoryDetector;

    public DefaultProjectScanner(BuildToolDetector buildToolDetector, SourceDirectoryDetector sourceDirectoryDetector) {
        this.buildToolDetector = buildToolDetector;
        this.sourceDirectoryDetector = sourceDirectoryDetector;
    }

    @Override
    public ProjectMetadata scan(Path projectRoot) {

        ProjectMetadata metadata = new ProjectMetadata();

        metadata.setProjectRoot(projectRoot);
        metadata.setStatus(ScanStatus.IN_PROGRESS);

        metadata.setProjectLayout(
                sourceDirectoryDetector.detect(projectRoot)
        );
        metadata.setBuildTool(
                buildToolDetector.detect(projectRoot)
        );

        metadata.setStatus(ScanStatus.COMPLETED);

        return metadata;
    }
}