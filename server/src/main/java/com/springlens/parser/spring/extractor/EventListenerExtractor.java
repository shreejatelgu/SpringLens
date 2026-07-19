package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaMethod;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.SpringEventListener;
import com.springlens.parser.spring.util.SpringAnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventListenerExtractor {

    public List<SpringEventListener> extract(JavaProject project) {

        List<SpringEventListener> listeners = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            for (JavaMethod method : javaClass.methods()) {

                if (SpringAnnotationUtils.hasAnnotation(
                        method,
                        SpringConstants.EVENT_LISTENER
                )) {

                    listeners.add(
                            new SpringEventListener(
                                    javaClass.name(),
                                    javaClass.packageName(),
                                    method.name()
                            )
                    );

                }

            }

        }

        return List.copyOf(listeners);

    }

}