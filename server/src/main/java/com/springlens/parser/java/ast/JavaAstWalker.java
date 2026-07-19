package com.springlens.parser.java.ast;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JavaAstWalker {

    public List<ClassOrInterfaceDeclaration> getClasses(
            CompilationUnit compilationUnit
    ) {

        return compilationUnit.findAll(ClassOrInterfaceDeclaration.class);

    }

}