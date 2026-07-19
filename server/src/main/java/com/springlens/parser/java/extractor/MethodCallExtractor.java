package com.springlens.parser.java.extractor;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.springlens.parser.java.model.JavaMethodCall;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MethodCallExtractor {

    public List<JavaMethodCall> extract(
            String className,
            MethodDeclaration method
    ) {

        List<JavaMethodCall> calls = new ArrayList<>();

        String callerMethod = method.getNameAsString();

        for (MethodCallExpr call : method.findAll(MethodCallExpr.class)) {

            String targetObject = "this";

            Optional<Expression> scope = call.getScope();

            if (scope.isPresent()) {
                targetObject = scope.get().toString();
            }

            calls.add(
                    new JavaMethodCall(
                            className,
                            callerMethod,
                            targetObject,
                            call.getNameAsString()
                    )
            );

        }

        return List.copyOf(calls);

    }

}