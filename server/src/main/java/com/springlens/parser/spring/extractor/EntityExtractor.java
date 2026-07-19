package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.SpringEntity;
import com.springlens.parser.spring.util.SpringAnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntityExtractor {

    public List<SpringEntity> extract(JavaProject project) {

        List<SpringEntity> entities = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            if (SpringAnnotationUtils.hasAnnotation(
                    javaClass,
                    SpringConstants.ENTITY
            )) {

                entities.add(

                        new SpringEntity(

                                javaClass.name(),

                                javaClass.packageName()

                        )

                );

            }

        }

        return List.copyOf(entities);

    }

}