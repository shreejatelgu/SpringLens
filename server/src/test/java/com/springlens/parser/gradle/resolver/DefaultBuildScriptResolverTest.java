package com.springlens.parser.gradle.resolver;

import com.springlens.parser.gradle.exception.GradleParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DefaultBuildScriptResolverTest {

    private final BuildScriptResolver resolver = new DefaultBuildScriptResolver();

    @TempDir
    Path projectRoot;

    @Test
    void shouldResolveKotlinDslWhenPresent() throws IOException {

        Path kotlinBuildFile = Files.createFile(projectRoot.resolve("build.gradle.kts"));

        Path resolved = resolver.resolve(projectRoot);

        assertEquals(kotlinBuildFile, resolved);
    }

    @Test
    void shouldResolveGroovyDslWhenKotlinDslAbsent() throws IOException {

        Path groovyBuildFile = Files.createFile(projectRoot.resolve("build.gradle"));

        Path resolved = resolver.resolve(projectRoot);

        assertEquals(groovyBuildFile, resolved);
    }

    @Test
    void shouldPreferKotlinDslWhenBothBuildScriptsExist() throws IOException {

        Files.createFile(projectRoot.resolve("build.gradle"));
        Path kotlinBuildFile = Files.createFile(projectRoot.resolve("build.gradle.kts"));

        Path resolved = resolver.resolve(projectRoot);

        assertEquals(kotlinBuildFile, resolved);
    }

    @Test
    void shouldThrowExceptionWhenNoBuildScriptExists() {

        assertThrows(
                GradleParseException.class,
                () -> resolver.resolve(projectRoot)
        );
    }

    @Test
    void shouldThrowExceptionWhenProjectRootIsNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> resolver.resolve(null)
        );
    }

}