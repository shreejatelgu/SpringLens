package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.model.SpringService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceExtractor {

    public List<SpringService> extract(JavaProject project) {

        List<SpringService> services = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            boolean isService =
                    javaClass.annotations()
                            .stream()
                            .anyMatch(annotation ->
                                    "Service".equals(annotation.name())
                            );

            if (isService) {

                services.add(
                        new SpringService(
                                javaClass.name(),
                                javaClass.packageName()
                        )
                );

            }

        }

        return services;
    }

}