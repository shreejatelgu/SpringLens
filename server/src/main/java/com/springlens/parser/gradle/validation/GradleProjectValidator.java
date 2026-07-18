package com.springlens.parser.gradle.validation;

import com.springlens.parser.gradle.model.GradleProjectInfo;
import org.springframework.stereotype.Component;

@Component
public class GradleProjectValidator {

    public void validate(GradleProjectInfo projectInfo) {

        if (projectInfo == null) {
            throw new IllegalArgumentException("Gradle project cannot be null.");
        }

        if (projectInfo.projectInfo() == null) {
            throw new IllegalStateException("Project information missing.");
        }

    }

}