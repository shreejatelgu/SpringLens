package com.springlens.parser.spring;

import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.model.SpringProject;
import org.springframework.stereotype.Component;


@Component
public class SpringParserFacade {

    private final SpringProjectAnalyzer analyzer;

    public SpringParserFacade(
            SpringProjectAnalyzer analyzer
    ) {
        this.analyzer = analyzer;
    }

    public SpringProject parse(
            JavaProject project
    ) {
        return analyzer.analyze(project);
    }

}