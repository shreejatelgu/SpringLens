package com.springlens.parser.java.extractor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaMethodCall;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ClassExtractor {

    private final FieldExtractor fieldExtractor;
    private final ConstructorExtractor constructorExtractor;
    private final MethodExtractor methodExtractor;
    private final MethodCallExtractor methodCallExtractor;
    private final AnnotationExtractor annotationExtractor;

    public ClassExtractor(
            FieldExtractor fieldExtractor,
            ConstructorExtractor constructorExtractor,
            MethodExtractor methodExtractor,
            MethodCallExtractor methodCallExtractor,
            AnnotationExtractor annotationExtractor
    ) {
        this.fieldExtractor = fieldExtractor;
        this.constructorExtractor = constructorExtractor;
        this.methodExtractor = methodExtractor;
        this.methodCallExtractor = methodCallExtractor;
        this.annotationExtractor = annotationExtractor;
    }

    public List<JavaClass> extract(
            CompilationUnit compilationUnit
    ) {

        List<JavaClass> classes = new ArrayList<>();

        String packageName = "";

        Optional<PackageDeclaration> packageDeclaration =
                compilationUnit.getPackageDeclaration();

        if (packageDeclaration.isPresent()) {
            packageName = packageDeclaration.get().getNameAsString();
        }

        for (ClassOrInterfaceDeclaration declaration
                : compilationUnit.findAll(ClassOrInterfaceDeclaration.class)) {

            if (declaration.isInterface()) {
                continue;
            }

            List<JavaMethodCall> methodCalls = new ArrayList<>();

            for (MethodDeclaration method : declaration.getMethods()) {

                methodCalls.addAll(
                        methodCallExtractor.extract(
                                declaration.getNameAsString(),
                                method
                        )
                );

            }

            String superClass = null;

if (declaration.getExtendedTypes().isNonEmpty()) {

    superClass =
            declaration.getExtendedTypes()
                    .get(0)
                    .getNameAsString();

}

List<String> implementedInterfaces = new ArrayList<>();

                declaration.getImplementedTypes()
                        .forEach(type ->
                                implementedInterfaces.add(
                                        type.getNameAsString()
                                )
        );

            classes.add(

                    new JavaClass(

        declaration.getNameAsString(),

        packageName,

        declaration.isAbstract(),

        declaration.isFinal(),

        superClass,

        implementedInterfaces,

        annotationExtractor.extract(declaration),

        fieldExtractor.extract(declaration),

        constructorExtractor.extract(declaration),

        methodExtractor.extract(declaration),

        methodCalls

)

            );

        }

        return List.copyOf(classes);

    }

}