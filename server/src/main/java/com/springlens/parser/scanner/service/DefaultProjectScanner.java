package com.springlens.parser.scanner.service;

import com.springlens.parser.maven.MavenParser;
import com.springlens.parser.maven.model.MavenProjectInfo;
import com.springlens.parser.scanner.detector.BuildToolDetector;
import com.springlens.parser.scanner.detector.FrameworkDetector;
import com.springlens.parser.scanner.detector.JavaVersionDetector;
import com.springlens.parser.scanner.detector.SourceDirectoryDetector;
import com.springlens.parser.scanner.model.BuildTool;
import com.springlens.parser.scanner.model.ProjectMetadata;
import com.springlens.parser.scanner.model.ScanStatus;
import com.springlens.parser.scanner.resolver.ProjectRootResolver;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class DefaultProjectScanner implements ProjectScanner {

    private final BuildToolDetector buildToolDetector;
    private final SourceDirectoryDetector sourceDirectoryDetector;
    private final ProjectRootResolver projectRootResolver;
    private final JavaVersionDetector javaVersionDetector;
    private final FrameworkDetector frameworkDetector;
    private final MavenParser mavenParser;

    public DefaultProjectScanner(
            BuildToolDetector buildToolDetector,
            SourceDirectoryDetector sourceDirectoryDetector,
            ProjectRootResolver projectRootResolver,
            JavaVersionDetector javaVersionDetector,
            FrameworkDetector frameworkDetector,
            MavenParser mavenParser
    ) {
        this.buildToolDetector = buildToolDetector;
        this.sourceDirectoryDetector = sourceDirectoryDetector;
        this.projectRootResolver = projectRootResolver;
        this.javaVersionDetector = javaVersionDetector;
        this.frameworkDetector = frameworkDetector;
        this.mavenParser = mavenParser;
    }

    @Override
    public ProjectMetadata scan(Path projectDirectory) {

        Path root = projectRootResolver.resolve(projectDirectory);

        ProjectMetadata metadata = new ProjectMetadata();
        metadata.setProjectRoot(root);
        metadata.setStatus(ScanStatus.IN_PROGRESS);

        metadata.setProjectLayout(
                sourceDirectoryDetector.detect(root)
        );

        BuildTool buildTool = buildToolDetector.detect(root);

        metadata.setBuildTool(buildTool);

        if (buildTool == BuildTool.MAVEN) {

            MavenProjectInfo projectInfo = mavenParser.parse(root);

            metadata.setJavaVersion(
                    javaVersionDetector.detect(projectInfo)
            );

            metadata.setFramework(
                    frameworkDetector.detect(projectInfo)
            );

            metadata.setSpringBootVersion(
                    projectInfo.getSpringBootVersion()
            );
        }

        metadata.setStatus(ScanStatus.COMPLETED);

        return metadata;
    }
}