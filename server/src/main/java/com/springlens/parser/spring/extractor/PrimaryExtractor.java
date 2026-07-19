package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.SpringPrimary;
import com.springlens.parser.spring.util.SpringAnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrimaryExtractor {

    public List<SpringPrimary> extract(JavaProject project) {

        List<SpringPrimary> primaries = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            if (SpringAnnotationUtils.hasAnnotation(
                    javaClass,
                    SpringConstants.PRIMARY
            )) {

                primaries.add(
                        new SpringPrimary(
                                javaClass.name(),
                                javaClass.packageName()
                        )
                );

            }

        }

        return List.copyOf(primaries);

    }

}