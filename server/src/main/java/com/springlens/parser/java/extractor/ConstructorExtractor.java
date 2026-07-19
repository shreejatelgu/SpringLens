package com.springlens.parser.java.extractor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.springlens.parser.java.model.JavaAnnotation;
import com.springlens.parser.java.model.JavaConstructor;
import com.springlens.parser.java.model.JavaParameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConstructorExtractor {

    public List<JavaConstructor> extract(
            ClassOrInterfaceDeclaration declaration
    ) {

        List<JavaConstructor> constructors = new ArrayList<>();

        for (ConstructorDeclaration constructor
                : declaration.getConstructors()) {

            List<JavaAnnotation> annotations = new ArrayList<>();

            constructor.getAnnotations().forEach(annotation ->
                    annotations.add(
                            new JavaAnnotation(
                                    annotation.getNameAsString()
                            )
                    )
            );

            List<JavaParameter> parameters = new ArrayList<>();

            constructor.getParameters().forEach(parameter ->
                    parameters.add(
                            new JavaParameter(
                                    parameter.getNameAsString(),
                                    parameter.getType().asString()
                            )
                    )
            );

            constructors.add(
                    new JavaConstructor(
                            constructor.getNameAsString(),
                            visibility(constructor),
                            annotations,
                            parameters
                    )
            );

        }

        return List.copyOf(constructors);

    }

    private String visibility(
            ConstructorDeclaration constructor
    ) {

        if (constructor.isPublic()) {
            return "public";
        }

        if (constructor.isProtected()) {
            return "protected";
        }

        if (constructor.isPrivate()) {
            return "private";
        }

        return "package-private";

    }

}