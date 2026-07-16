package com.springlens.parser.maven;

import com.springlens.parser.maven.model.MavenProjectInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultMavenParserTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldParseBasicPom() throws Exception {

        String pom = """
                <project>
                    <modelVersion>4.0.0</modelVersion>
                    <groupId>com.example</groupId>
                    <artifactId>demo</artifactId>
                    <version>1.0.0</version>
                    <packaging>jar</packaging>
                </project>
                """;

        Files.writeString(
                tempDir.resolve("pom.xml"),
                pom
        );

        DefaultMavenParser parser = new DefaultMavenParser();

        MavenProjectInfo info = parser.parse(tempDir);

        assertEquals("com.example", info.getGroupId());
        assertEquals("demo", info.getArtifactId());
        assertEquals("1.0.0", info.getVersion());
        assertEquals("jar", info.getPackaging());
    }
}