package com.springlens.parser.java.extractor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.springlens.parser.java.model.JavaEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnumExtractor {

    public List<JavaEnum> extract(CompilationUnit compilationUnit) {

        String packageName = compilationUnit.getPackageDeclaration()
                .map(pd -> pd.getNameAsString())
                .orElse("");

        return compilationUnit.findAll(EnumDeclaration.class)
                .stream()
                .map(e -> new JavaEnum(
                        e.getNameAsString(),
                        packageName
                ))
                .toList();
    }

}