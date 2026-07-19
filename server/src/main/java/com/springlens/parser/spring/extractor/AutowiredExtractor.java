package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaField;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.model.AutowiredDependency;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AutowiredExtractor {

    public List<AutowiredDependency> extract(JavaProject project) {

        List<AutowiredDependency> dependencies = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            for (JavaField field : javaClass.fields()) {

                boolean isAutowired =
                        field.annotations()
                                .stream()
                                .anyMatch(annotation ->
                                        "Autowired".equals(annotation.name())
                                );

                if (isAutowired) {

                    dependencies.add(
                            new AutowiredDependency(
                                    javaClass.name(),
                                    javaClass.packageName(),
                                    field.name(),
                                    field.type()
                            )
                    );

                }

            }

        }

        return dependencies;
    }

}