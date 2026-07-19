package com.springlens.parser.java.extractor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.springlens.parser.java.model.JavaAnnotation;
import com.springlens.parser.java.model.JavaField;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FieldExtractor {

    public List<JavaField> extract(
            ClassOrInterfaceDeclaration declaration
    ) {

        List<JavaField> fields = new ArrayList<>();

        for (FieldDeclaration field : declaration.getFields()) {

            List<JavaAnnotation> annotations = new ArrayList<>();

            field.getAnnotations().forEach(annotation ->
                    annotations.add(
                            new JavaAnnotation(
                                    annotation.getNameAsString()
                            )
                    )
            );

            field.getVariables().forEach(variable ->
                    fields.add(
                            new JavaField(
                                    variable.getNameAsString(),
                                    variable.getType().asString(),
                                    field.isStatic(),
                                    field.isFinal(),
                                    visibility(field),
                                    annotations
                            )
                    )
            );

        }

        return List.copyOf(fields);

    }

    private String visibility(FieldDeclaration field) {

        if (field.isPublic()) {
            return "public";
        }

        if (field.isProtected()) {
            return "protected";
        }

        if (field.isPrivate()) {
            return "private";
        }

        return "package-private";

    }

}