package com.springlens.parser.java.validation;

import com.springlens.parser.java.model.JavaProject;
import org.springframework.stereotype.Component;

@Component
public class JavaProjectValidator {

    public void validate(JavaProject project) {

        if (project == null) {
            throw new IllegalArgumentException(
                    "Java project cannot be null."
            );
        }

        if (project.packages().isEmpty()
                && project.classes().isEmpty()
                && project.interfaces().isEmpty()) {

            throw new IllegalStateException(
                    "No Java source found."
            );

        }

    }

}