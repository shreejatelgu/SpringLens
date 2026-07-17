package com.springlens.parser.scanner.service;

import com.springlens.parser.maven.extractor.SpringBootVersionExtractor;
import com.springlens.parser.maven.model.MavenProjectInfo;
import com.springlens.parser.scanner.detector.BuildToolDetector;
import com.springlens.parser.scanner.detector.FrameworkDetector;
import com.springlens.parser.scanner.detector.JavaVersionDetector;
import com.springlens.parser.scanner.detector.SourceDirectoryDetector;
import com.springlens.parser.scanner.model.ProjectMetadata;
import com.springlens.parser.scanner.model.ScanStatus;
import com.springlens.parser.scanner.resolver.ProjectRootResolver;
import com.springlens.parser.maven.MavenParser;

import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class DefaultProjectScanner implements ProjectScanner {

    private final SpringBootVersionExtractor springBootVersionExtractor;
    private final FrameworkDetector frameworkDetector;
    private final JavaVersionDetector javaVersionDetector;
    private final BuildToolDetector buildToolDetector;
    private final SourceDirectoryDetector sourceDirectoryDetector;
    private final ProjectRootResolver projectRootResolver;
    private final MavenParser mavenParser;


        public DefaultProjectScanner(BuildToolDetector buildToolDetector, SourceDirectoryDetector sourceDirectoryDetector, ProjectRootResolver projectRootResolver, JavaVersionDetector javaVersionDetector, FrameworkDetector frameworkDetector, MavenParser mavenParser, SpringBootVersionExtractor springBootVersionExtractor) {
                this.buildToolDetector = buildToolDetector;
                this.sourceDirectoryDetector = sourceDirectoryDetector;
                this.projectRootResolver = projectRootResolver;
                this.javaVersionDetector = javaVersionDetector;
                this.frameworkDetector = frameworkDetector;
                this.mavenParser = mavenParser;
                this.springBootVersionExtractor = springBootVersionExtractor;
    }

    @Override
    public ProjectMetadata scan(Path projectRoot) {

        ProjectMetadata metadata = new ProjectMetadata();

        Path root = projectRootResolver.resolve(projectRoot);
        metadata.setProjectRoot(root);
        metadata.setStatus(ScanStatus.IN_PROGRESS);

        metadata.setProjectLayout(
                sourceDirectoryDetector.detect(projectRoot)
        );
        metadata.setBuildTool(
                buildToolDetector.detect(projectRoot)
        );

        metadata.setStatus(ScanStatus.COMPLETED);

        MavenProjectInfo projectInfo = mavenParser.parse(projectRoot);

        metadata.setJavaVersion(
                javaVersionDetector.detect(projectInfo)
        );

        metadata.setFramework(
                frameworkDetector.detect(projectInfo)
        );

        metadata.setSpringBootVersion(
        springBootVersionExtractor.extract(projectInfo)
        );
        return metadata;
    }
}