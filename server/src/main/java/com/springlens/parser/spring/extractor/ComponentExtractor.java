package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.model.SpringComponent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ComponentExtractor {

    public List<SpringComponent> extract(JavaProject project) {

        List<SpringComponent> components = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            boolean isComponent =
                    javaClass.annotations()
                            .stream()
                            .anyMatch(annotation ->
                                    "Component".equals(annotation.name())
                            );

            if (isComponent) {

                components.add(
                        new SpringComponent(
                                javaClass.name(),
                                javaClass.packageName()
                        )
                );

            }

        }

        return components;
    }

}