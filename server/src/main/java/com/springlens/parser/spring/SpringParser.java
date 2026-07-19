package com.springlens.parser.spring;

import com.springlens.parser.spring.model.SpringProject;
import com.springlens.parser.java.model.JavaProject;

public interface SpringParser {

    SpringProject parse(JavaProject javaProject);

}