package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.SpringLazy;
import com.springlens.parser.spring.util.SpringAnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LazyExtractor {

    public List<SpringLazy> extract(JavaProject project) {

        List<SpringLazy> lazyBeans = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            if (SpringAnnotationUtils.hasAnnotation(
                    javaClass,
                    SpringConstants.LAZY
            )) {

                lazyBeans.add(
                        new SpringLazy(
                                javaClass.name(),
                                javaClass.packageName()
                        )
                );

            }

        }

        return List.copyOf(lazyBeans);

    }

}