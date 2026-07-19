package com.springlens.parser.java.ast;

import com.github.javaparser.ast.CompilationUnit;

public record JavaAstContext(

        CompilationUnit compilationUnit

) {
}