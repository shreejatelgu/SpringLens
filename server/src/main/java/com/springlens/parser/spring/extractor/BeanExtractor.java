package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaMethod;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.model.SpringBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BeanExtractor {

    public List<SpringBean> extract(JavaProject project) {

        List<SpringBean> beans = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            for (JavaMethod method : javaClass.methods()) {

                boolean isBean =
                        method.annotations()
                                .stream()
                                .anyMatch(annotation ->
                                        "Bean".equals(annotation.name())
                                );

                if (isBean) {

                    beans.add(
                            new SpringBean(
                                    method.name(),
                                    method.returnType(),
                                    javaClass.name(),
                                    javaClass.packageName()
                            )
                    );

                }

            }

        }

        return beans;
    }

}