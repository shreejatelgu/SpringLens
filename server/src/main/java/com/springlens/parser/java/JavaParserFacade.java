package com.springlens.parser.java;

import com.springlens.parser.java.model.JavaProject;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class JavaParserFacade {

    private final JavaProjectAnalyzer analyzer;

    public JavaParserFacade(
            JavaProjectAnalyzer analyzer
    ) {
        this.analyzer = analyzer;
    }

    public JavaProject parse(Path sourceRoot) {
        return analyzer.analyze(sourceRoot);
    }

}