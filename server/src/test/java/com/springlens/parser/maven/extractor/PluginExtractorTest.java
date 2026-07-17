package com.springlens.parser.maven.extractor;

import com.springlens.parser.maven.model.MavenPlugin;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
/* Here we are testing our own class not the spring boot we should do ths to spring lens because it test driven  */
class PluginExtractorTest {

    @Test
    void shouldExtractPlugins() throws Exception {

        String xml = """
                <project>

<build>

<plugins>

<plugin>

<groupId>org.springframework.boot</groupId>

<artifactId>spring-boot-maven-plugin</artifactId>

<version>3.5.16</version>

</plugin>

</plugins>

</build>

</project>
                """;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(
                new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))
        );

        PluginExtractor extractor = new PluginExtractor();

        List<MavenPlugin> plugins = extractor.extract(document);

       assertEquals(1, plugins.size());

assertEquals(
        "spring-boot-maven-plugin",
        plugins.get(0).getArtifactId()
);

assertEquals(
        "3.5.16",
        plugins.get(0).getVersion()
);
    }
}