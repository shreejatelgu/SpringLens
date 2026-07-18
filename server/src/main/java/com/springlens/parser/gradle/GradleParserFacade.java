package com.springlens.parser.gradle;

import com.springlens.parser.gradle.model.GradleProjectInfo;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class GradleParserFacade {

    private final GradleProjectAnalyzer analyzer;

    public GradleParserFacade(
            GradleProjectAnalyzer analyzer
    ) {
        this.analyzer = analyzer;
    }

    public GradleProjectInfo parse(Path projectRoot) {
        return analyzer.analyze(projectRoot);
    }

}