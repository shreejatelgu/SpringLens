package com.springlens.parser.java;

import com.github.javaparser.ast.CompilationUnit;
import com.springlens.parser.java.ast.CompilationUnitLoader;
import com.springlens.parser.java.extractor.ClassExtractor;
import com.springlens.parser.java.extractor.EnumExtractor;
import com.springlens.parser.java.extractor.ImportExtractor;
import com.springlens.parser.java.extractor.InterfaceExtractor;
import com.springlens.parser.java.extractor.PackageExtractor;
import com.springlens.parser.java.extractor.RecordExtractor;
import com.springlens.parser.java.model.JavaProject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultJavaParser implements JavaParser {

    private final CompilationUnitLoader compilationUnitLoader;
    private final PackageExtractor packageExtractor;
    private final ImportExtractor importExtractor;
    private final ClassExtractor classExtractor;
    private final InterfaceExtractor interfaceExtractor;
    private final EnumExtractor enumExtractor;
    private final RecordExtractor recordExtractor;

    public DefaultJavaParser(
            CompilationUnitLoader compilationUnitLoader,
            PackageExtractor packageExtractor,
            ImportExtractor importExtractor,
            ClassExtractor classExtractor,
            InterfaceExtractor interfaceExtractor,
            EnumExtractor enumExtractor,
            RecordExtractor recordExtractor
    ) {
        this.compilationUnitLoader = compilationUnitLoader;
        this.packageExtractor = packageExtractor;
        this.importExtractor = importExtractor;
        this.classExtractor = classExtractor;
        this.interfaceExtractor = interfaceExtractor;
        this.enumExtractor = enumExtractor;
        this.recordExtractor = recordExtractor;
    }

    @Override
    public JavaProject parse(Path sourceRoot) {

        List<String> packages = new ArrayList<>();
        List<String> imports = new ArrayList<>();

        var classes = new ArrayList<com.springlens.parser.java.model.JavaClass>();
        var interfaces = new ArrayList<com.springlens.parser.java.model.JavaInterface>();
        var enums = new ArrayList<com.springlens.parser.java.model.JavaEnum>();
        var records = new ArrayList<com.springlens.parser.java.model.JavaRecord>();

        try {

            Files.walk(sourceRoot)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(javaFile -> {

                        CompilationUnit compilationUnit =
                                compilationUnitLoader.load(javaFile);

                        String packageName =
                                packageExtractor.extract(compilationUnit);

                        if (packageName != null) {
                            packages.add(packageName);
                        }

                        imports.addAll(
                                importExtractor.extract(compilationUnit)
                        );

                        classes.addAll(
                                classExtractor.extract(compilationUnit)
                        );

                        interfaces.addAll(
                                interfaceExtractor.extract(compilationUnit)
                        );

                        enums.addAll(
                                enumExtractor.extract(compilationUnit)
                        );

                        records.addAll(
                                recordExtractor.extract(compilationUnit)
                        );

                    });

        } catch (IOException ex) {

            throw new IllegalStateException(
                    "Failed to parse Java project.",
                    ex
            );

        }

        return new JavaProject(

                packages.stream().distinct().toList(),

                imports.stream().distinct().toList(),

                classes,

                interfaces,

                enums,

                records

        );

    }

}