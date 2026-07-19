package com.springlens.parser.java.extractor;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.springlens.parser.java.model.JavaAnnotation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnnotationExtractor {

    public List<JavaAnnotation> extract(
            NodeWithAnnotations<?> node
    ) {

        List<JavaAnnotation> annotations =
                new ArrayList<>();

        for (AnnotationExpr annotation :
                node.getAnnotations()) {

            annotations.add(

                    new JavaAnnotation(
                            annotation.getNameAsString()
                    )

            );

        }

        return List.copyOf(annotations);

    }

}