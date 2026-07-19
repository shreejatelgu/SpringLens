package com.springlens.parser.java;

import com.springlens.parser.java.model.JavaProject;

import java.nio.file.Path;

public interface JavaParser {

    JavaProject parse(Path sourceRoot);

}