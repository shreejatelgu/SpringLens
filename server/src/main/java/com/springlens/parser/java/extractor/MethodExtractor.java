package com.springlens.parser.java.extractor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.springlens.parser.java.model.JavaAnnotation;
import com.springlens.parser.java.model.JavaMethod;
import com.springlens.parser.java.model.JavaParameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MethodExtractor {

    public List<JavaMethod> extract(
            ClassOrInterfaceDeclaration declaration
    ) {

        List<JavaMethod> methods = new ArrayList<>();

        for (MethodDeclaration method : declaration.getMethods()) {

            List<JavaAnnotation> annotations = new ArrayList<>();

            method.getAnnotations().forEach(annotation ->
                    annotations.add(
                            new JavaAnnotation(
                                    annotation.getNameAsString()
                            )
                    )
            );

            List<JavaParameter> parameters = new ArrayList<>();

            method.getParameters().forEach(parameter ->
                    parameters.add(
                            new JavaParameter(
                                    parameter.getNameAsString(),
                                    parameter.getType().asString()
                            )
                    )
            );

            methods.add(

                    new JavaMethod(

                            method.getNameAsString(),

                            method.getType().asString(),

                            visibility(method),

                            method.isStatic(),

                            method.isAbstract(),

                            annotations,

                            parameters

                    )

            );

        }

        return List.copyOf(methods);

    }

    private String visibility(MethodDeclaration method) {

        if (method.isPublic()) {
            return "public";
        }

        if (method.isProtected()) {
            return "protected";
        }

        if (method.isPrivate()) {
            return "private";
        }

        return "package-private";

    }

}