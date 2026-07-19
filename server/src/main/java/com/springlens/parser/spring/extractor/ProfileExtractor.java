package com.springlens.parser.spring.extractor;


import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.SpringProfile;
import com.springlens.parser.spring.util.SpringAnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileExtractor {

    public List<SpringProfile> extract(JavaProject project) {

        List<SpringProfile> profiles = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            if (SpringAnnotationUtils.hasAnnotation(
                    javaClass,
                    SpringConstants.PROFILE
            )) {

                profiles.add(
                        new SpringProfile(
                                javaClass.name(),
                                javaClass.packageName(),
                                SpringConstants.PROFILE
                        )
                );

            }

        }

        return List.copyOf(profiles);

    }

}