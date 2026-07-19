package com.springlens.parser.java.extractor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.RecordDeclaration;
import com.springlens.parser.java.model.JavaRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecordExtractor {

    public List<JavaRecord> extract(CompilationUnit compilationUnit) {

        String packageName = compilationUnit.getPackageDeclaration()
                .map(pd -> pd.getNameAsString())
                .orElse("");

        return compilationUnit.findAll(RecordDeclaration.class)
                .stream()
                .map(r -> new JavaRecord(
                        r.getNameAsString(),
                        packageName
                ))
                .toList();
    }

}