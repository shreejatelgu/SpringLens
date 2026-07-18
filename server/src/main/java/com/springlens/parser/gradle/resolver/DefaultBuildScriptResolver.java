package com.springlens.parser.gradle.resolver;

import com.springlens.parser.gradle.exception.GradleParseException;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class DefaultBuildScriptResolver implements BuildScriptResolver {

    private static final String KOTLIN_DSL = "build.gradle.kts";
    private static final String GROOVY_DSL = "build.gradle";

    @Override
    public Path resolve(Path projectRoot) {

        if (projectRoot == null) {
            throw new IllegalArgumentException("Project root cannot be null.");
        }

        Path kotlinDsl = projectRoot.resolve(KOTLIN_DSL);

        if (Files.isRegularFile(kotlinDsl)) {
            return kotlinDsl;
        }

        Path groovyDsl = projectRoot.resolve(GROOVY_DSL);

        if (Files.isRegularFile(groovyDsl)) {
            return groovyDsl;
        }

        throw new GradleParseException(
                "No supported Gradle build script found in project: "
                        + projectRoot.toAbsolutePath()
        );
    }

}