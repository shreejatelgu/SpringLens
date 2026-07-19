package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.model.ConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigurationPropertyExtractor {

    public List<ConfigurationProperty> extract(JavaProject project) {

        List<ConfigurationProperty> properties = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            javaClass.annotations()
                    .stream()
                    .filter(annotation ->
                            "ConfigurationProperties".equals(annotation.name())
                    )
                    .findFirst()
                    .ifPresent(annotation ->
                            properties.add(
                                    new ConfigurationProperty(
                                            javaClass.name(),
                                            javaClass.packageName()
                                    )
                            )
                    );

        }

        return properties;
    }

}