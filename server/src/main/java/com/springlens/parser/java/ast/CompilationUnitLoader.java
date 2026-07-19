package com.springlens.parser.java.ast;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class CompilationUnitLoader {

    private final JavaParser javaParser;

    public CompilationUnitLoader() {
        this.javaParser = new JavaParser();
    }

    public CompilationUnit load(Path javaFile) {

        try {

            ParseResult<CompilationUnit> result =
                    javaParser.parse(Files.readString(javaFile));

            return result.getResult()
                    .orElseThrow(() ->
                            new IllegalStateException(
                                    "Unable to parse: " + javaFile
                            ));

        } catch (IOException ex) {

            throw new IllegalStateException(
                    "Failed to read Java file.",
                    ex
            );

        }

    }

}