package com.springlens.parser.java.extractor;

import com.github.javaparser.ast.CompilationUnit;
import org.springframework.stereotype.Component;

@Component
public class PackageExtractor {

    public String extract(CompilationUnit compilationUnit) {

        return compilationUnit.getPackageDeclaration()
                .map(packageDeclaration ->
                        packageDeclaration.getNameAsString())
                .orElse(null);

    }

}