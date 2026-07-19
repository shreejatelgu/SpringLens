package com.springlens.parser.spring.validation;

import com.springlens.parser.spring.model.SpringProject;
import org.springframework.stereotype.Component;

@Component
public class SpringProjectValidator {

    public void validate(SpringProject project) {

        if (project == null) {
            throw new IllegalArgumentException("Spring project cannot be null.");
        }

    }

}