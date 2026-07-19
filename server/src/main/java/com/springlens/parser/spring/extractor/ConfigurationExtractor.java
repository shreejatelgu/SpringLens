package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.model.SpringConfiguration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigurationExtractor {

    public List<SpringConfiguration> extract(JavaProject project) {

        List<SpringConfiguration> configurations = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            boolean isConfiguration =
                    javaClass.annotations()
                            .stream()
                            .anyMatch(annotation ->
                                    "Configuration".equals(annotation.name())
                            );

            if (isConfiguration) {

                configurations.add(
                        new SpringConfiguration(
                                javaClass.name(),
                                javaClass.packageName()
                        )
                );

            }

        }

        return configurations;
    }

}