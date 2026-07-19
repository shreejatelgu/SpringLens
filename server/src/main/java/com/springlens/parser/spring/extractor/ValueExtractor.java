package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaAnnotation;
import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaField;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.SpringValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValueExtractor {

    public List<SpringValue> extract(JavaProject project) {

        List<SpringValue> values = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            for (JavaField field : javaClass.fields()) {

                for (JavaAnnotation annotation : field.annotations()) {

                    if (SpringConstants.VALUE.equals(annotation.name())) {

                        values.add(
                                new SpringValue(
                                        javaClass.name(),
                                        field.name()
                                )
                        );

                    }

                }

            }

        }

        return List.copyOf(values);

    }

}