package com.springlens.parser.java.extractor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.springlens.parser.java.model.JavaInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InterfaceExtractor {

    public List<JavaInterface> extract(CompilationUnit compilationUnit) {

        List<JavaInterface> interfaces = new ArrayList<>();

        String packageName = "";

        Optional<PackageDeclaration> packageDeclaration =
                compilationUnit.getPackageDeclaration();

        if (packageDeclaration.isPresent()) {
            packageName = packageDeclaration.get().getNameAsString();
        }

        for (ClassOrInterfaceDeclaration declaration
                : compilationUnit.findAll(ClassOrInterfaceDeclaration.class)) {

            if (!declaration.isInterface()) {
                continue;
            }

            interfaces.add(
                    new JavaInterface(
                            declaration.getNameAsString(),
                            packageName
                    )
            );
        }

        return List.copyOf(interfaces);
    }

}