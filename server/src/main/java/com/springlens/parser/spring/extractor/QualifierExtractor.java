package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaAnnotation;
import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaField;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.SpringQualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QualifierExtractor {

    public List<SpringQualifier> extract(JavaProject project) {

        List<SpringQualifier> qualifiers = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            for (JavaField field : javaClass.fields()) {

                for (JavaAnnotation annotation : field.annotations()) {

                    if (SpringConstants.QUALIFIER.equals(annotation.name())) {

                        qualifiers.add(
                                new SpringQualifier(
                                        javaClass.name(),
                                        field.name(),
                                        annotation.name()
                                )
                        );

                    }

                }

            }

        }

        return List.copyOf(qualifiers);

    }

}