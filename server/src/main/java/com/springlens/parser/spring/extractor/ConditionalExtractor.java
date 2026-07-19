package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaAnnotation;
import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.SpringConditional;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConditionalExtractor {

    private static final List<String> CONDITIONAL_ANNOTATIONS = List.of(

            SpringConstants.CONDITIONAL,

            SpringConstants.CONDITIONAL_ON_BEAN,

            SpringConstants.CONDITIONAL_ON_CLASS,

            SpringConstants.CONDITIONAL_ON_PROPERTY,

            SpringConstants.CONDITIONAL_ON_MISSING_BEAN

    );

    public List<SpringConditional> extract(JavaProject project) {

        List<SpringConditional> conditionals = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            for (JavaAnnotation annotation : javaClass.annotations()) {

                if (CONDITIONAL_ANNOTATIONS.contains(annotation.name())) {

                    conditionals.add(

                            new SpringConditional(

                                    javaClass.name(),

                                    javaClass.packageName(),

                                    annotation.name()

                            )

                    );

                }

            }

        }

        return List.copyOf(conditionals);

    }

}