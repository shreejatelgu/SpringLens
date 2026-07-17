package com.springlens.parser.maven;

import com.springlens.parser.maven.extractor.DependencyExtractor;
import com.springlens.parser.maven.extractor.JavaVersionExtractor;
import com.springlens.parser.maven.extractor.PluginExtractor;
import com.springlens.parser.maven.extractor.PropertyExtractor;
import com.springlens.parser.maven.extractor.SpringBootVersionExtractor;
import com.springlens.parser.maven.model.MavenProjectInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DefaultMavenParserTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldParseCompletePom() throws Exception {

        // Arrange

        String pom = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0"
                         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                         https://maven.apache.org/xsd/maven-4.0.0.xsd">

                    <modelVersion>4.0.0</modelVersion>

                    <parent>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-parent</artifactId>
                        <version>3.5.16</version>
                    </parent>

                    <groupId>com.springlens</groupId>
                    <artifactId>demo</artifactId>
                    <version>1.0.0</version>
                    <packaging>jar</packaging>

                    <properties>
                        <java.version>21</java.version>
                    </properties>

                    <dependencies>

                        <dependency>
                            <groupId>org.springframework.boot</groupId>
                            <artifactId>spring-boot-starter-web</artifactId>
                        </dependency>

                        <dependency>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <optional>true</optional>
                        </dependency>

                    </dependencies>

                    <build>

                        <plugins>

                            <plugin>
                                <groupId>org.springframework.boot</groupId>
                                <artifactId>spring-boot-maven-plugin</artifactId>
                            </plugin>

                        </plugins>

                    </build>

                </project>
                """;

        Files.writeString(
                tempDir.resolve("pom.xml"),
                pom
        );

        DefaultMavenParser parser =
                new DefaultMavenParser(
                        new JavaVersionExtractor(),
                        new SpringBootVersionExtractor(),
                        new PropertyExtractor(),
                        new DependencyExtractor(),
                        new PluginExtractor()
                );

        // Act

        MavenProjectInfo info = parser.parse(tempDir);

        // Assert

        assertNotNull(info);

        assertEquals("com.springlens", info.getGroupId());
        assertEquals("demo", info.getArtifactId());
        assertEquals("1.0.0", info.getVersion());
        assertEquals("jar", info.getPackaging());

        assertEquals("21", info.getJavaVersion());
        assertEquals("3.5.16", info.getSpringBootVersion());

        assertEquals(1, info.getProperties().size());
        assertEquals("java.version", info.getProperties().getFirst().getName());
        assertEquals("21", info.getProperties().getFirst().getValue());

        assertEquals(2, info.getDependencies().size());

        assertEquals(
                "spring-boot-starter-web",
                info.getDependencies().getFirst().getArtifactId()
        );

        assertEquals(
                "lombok",
                info.getDependencies().get(1).getArtifactId()
        );

        assertTrue(
                info.getDependencies().get(1).isOptional()
        );

        assertEquals(1, info.getPlugins().size());

        assertEquals(
                "spring-boot-maven-plugin",
                info.getPlugins().getFirst().getArtifactId()
        );
    }
}