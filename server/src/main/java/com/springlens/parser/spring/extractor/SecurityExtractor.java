package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaAnnotation;
import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaMethod;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.SpringSecurityComponent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityExtractor {

    private static final List<String> SECURITY_ANNOTATIONS = List.of(
            SpringConstants.ENABLE_WEB_SECURITY,
            SpringConstants.ENABLE_METHOD_SECURITY,
            SpringConstants.PRE_AUTHORIZE,
            SpringConstants.SECURED,
            SpringConstants.ROLES_ALLOWED
    );

    public List<SpringSecurityComponent> extract(JavaProject project) {

        List<SpringSecurityComponent> security = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            for (JavaAnnotation annotation : javaClass.annotations()) {

                if (SECURITY_ANNOTATIONS.contains(annotation.name())) {

                    security.add(
                            new SpringSecurityComponent(
                                    javaClass.name(),
                                    javaClass.packageName(),
                                    annotation.name()
                            )
                    );

                }

            }

            for (JavaMethod method : javaClass.methods()) {

                for (JavaAnnotation annotation : method.annotations()) {

                    if (SECURITY_ANNOTATIONS.contains(annotation.name())) {

                        security.add(
                                new SpringSecurityComponent(
                                        javaClass.name(),
                                        javaClass.packageName(),
                                        annotation.name()
                                )
                        );

                    }

                }

            }

        }

        return List.copyOf(security);

    }

}