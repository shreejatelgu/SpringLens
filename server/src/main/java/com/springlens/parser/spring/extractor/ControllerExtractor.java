package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.model.SpringController;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ControllerExtractor {

    private static final List<String> CONTROLLER_ANNOTATIONS = List.of(
            "Controller",
            "RestController"
    );

    public List<SpringController> extract(JavaProject project) {

        List<SpringController> controllers = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            if (hasAnnotation(javaClass)) {

                controllers.add(
                        new SpringController(
                                javaClass.name(),
                                javaClass.packageName()
                        )
                );

            }

        }

        return controllers;
    }

    private boolean hasAnnotation(JavaClass javaClass) {

        return javaClass.annotations()
                .stream()
                .anyMatch(annotation ->
                        CONTROLLER_ANNOTATIONS.contains(annotation.name())
                );

    }

}