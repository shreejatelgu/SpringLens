package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaMethod;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.SpringScheduled;
import com.springlens.parser.spring.util.SpringAnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledExtractor {

    public List<SpringScheduled> extract(JavaProject project) {

        List<SpringScheduled> scheduled = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            for (JavaMethod method : javaClass.methods()) {

                if (SpringAnnotationUtils.hasAnnotation(
                        method,
                        SpringConstants.SCHEDULED
                )) {

                    scheduled.add(
                            new SpringScheduled(
                                    javaClass.name(),
                                    javaClass.packageName(),
                                    method.name()
                            )
                    );

                }

            }

        }

        return List.copyOf(scheduled);

    }

}