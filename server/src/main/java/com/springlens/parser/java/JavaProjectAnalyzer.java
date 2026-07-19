package com.springlens.parser.java;

import com.springlens.parser.java.model.JavaProject;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class JavaProjectAnalyzer {

    private final JavaParser javaParser;

    public JavaProjectAnalyzer(
            JavaParser javaParser
    ) {
        this.javaParser = javaParser;
    }

    public JavaProject analyze(Path sourceRoot) {
        return javaParser.parse(sourceRoot);
    }

}