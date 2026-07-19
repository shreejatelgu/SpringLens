package com.springlens.parser.java.extractor;

import com.github.javaparser.ast.CompilationUnit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImportExtractor {

    public List<String> extract(CompilationUnit compilationUnit) {

        return compilationUnit.getImports()
                .stream()
                .map(importDeclaration ->
                        importDeclaration.getNameAsString())
                .toList();

    }

}