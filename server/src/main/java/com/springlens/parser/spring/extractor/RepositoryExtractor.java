package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.model.SpringRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RepositoryExtractor {

    public List<SpringRepository> extract(JavaProject project) {

        List<SpringRepository> repositories = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            boolean isRepository =
                    javaClass.annotations()
                            .stream()
                            .anyMatch(annotation ->
                                    "Repository".equals(annotation.name())
                            );

            if (isRepository) {

                repositories.add(
                        new SpringRepository(
                                javaClass.name(),
                                javaClass.packageName()
                        )
                );

            }

        }

        return repositories;
    }

}