package com.springlens.parser.gradle;

import com.springlens.parser.gradle.model.GradleProjectInfo;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class GradleProjectAnalyzer {

    private final GradleParser gradleParser;

    public GradleProjectAnalyzer(
            GradleParser gradleParser
    ) {
        this.gradleParser = gradleParser;
    }

    public GradleProjectInfo analyze(Path projectRoot) {
        return gradleParser.parse(projectRoot);
    }

}