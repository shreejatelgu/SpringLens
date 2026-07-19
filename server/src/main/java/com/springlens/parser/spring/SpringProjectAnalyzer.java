package com.springlens.parser.spring;

import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.model.SpringProject;
import org.springframework.stereotype.Component;

@Component
public class SpringProjectAnalyzer {

    private final SpringParser springParser;

    public SpringProjectAnalyzer(SpringParser springParser) {
        this.springParser = springParser;
    }

    public SpringProject analyze(JavaProject javaProject) {

        return springParser.parse(javaProject);

    }

}