package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaMethod;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.SpringTransactional;
import com.springlens.parser.spring.util.SpringAnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionalExtractor {

    public List<SpringTransactional> extract(JavaProject project) {

        List<SpringTransactional> transactionals = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            if (SpringAnnotationUtils.hasAnnotation(
                    javaClass,
                    SpringConstants.TRANSACTIONAL
            )) {

                transactionals.add(
                        new SpringTransactional(
                                javaClass.name(),
                                javaClass.packageName(),
                                null
                        )
                );
            }

            for (JavaMethod method : javaClass.methods()) {

                if (SpringAnnotationUtils.hasAnnotation(
                        method,
                        SpringConstants.TRANSACTIONAL
                )) {

                    transactionals.add(
                            new SpringTransactional(
                                    javaClass.name(),
                                    javaClass.packageName(),
                                    method.name()
                            )
                    );
                }
            }
        }

        return List.copyOf(transactionals);

    }

}