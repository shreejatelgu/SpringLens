package com.springlens.parser.scanner.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ProjectRootResolverTest {
    
    @TempDir
    Path tempDir;
    
    @Test
    void shouldResolveNestedProjectRoot() throws Exception {

        Path nested = tempDir.resolve("spring-petclinic");

        Files.createDirectories(nested);

        Files.createFile(nested.resolve("pom.xml"));

        ProjectRootResolver resolver = new ProjectRootResolver();

        Path root = resolver.resolve(tempDir);

        assertEquals(nested, root);

    }
}
