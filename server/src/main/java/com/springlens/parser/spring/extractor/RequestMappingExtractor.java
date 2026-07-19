package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaMethod;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.RequestMapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestMappingExtractor {

    public List<RequestMapping> extract(JavaProject project) {

        List<RequestMapping> mappings = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            for (JavaMethod method : javaClass.methods()) {

                method.annotations()
                        .stream()
                        .filter(annotation ->
                                SpringConstants.REQUEST_MAPPINGS.contains(annotation.name())
                        )
                        .findFirst()
                        .ifPresent(annotation ->
                                mappings.add(
                                        new RequestMapping(
                                                javaClass.name(),
                                                javaClass.packageName(),
                                                method.name(),
                                                annotation.name()
                                        )
                                )
                        );

            }

        }

        return mappings;
    }

}