package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaMethod;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.SpringAsync;
import com.springlens.parser.spring.util.SpringAnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AsyncExtractor {

    public List<SpringAsync> extract(JavaProject project) {

        List<SpringAsync> asyncMethods = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            for (JavaMethod method : javaClass.methods()) {

                if (SpringAnnotationUtils.hasAnnotation(
                        method,
                        SpringConstants.ASYNC
                )) {

                    asyncMethods.add(
                            new SpringAsync(
                                    javaClass.name(),
                                    javaClass.packageName(),
                                    method.name()
                            )
                    );

                }

            }

        }

        return List.copyOf(asyncMethods);

    }

}