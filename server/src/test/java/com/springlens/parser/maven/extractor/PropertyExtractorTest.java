package com.springlens.parser.maven.extractor;

import com.springlens.parser.maven.model.MavenProperty;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
/* Here we are testing our own class not the spring boot we should do ths to spring lens because it test driven  */
class PropertyExtractorTest {

    @Test
    void shouldExtractProperties() throws Exception {

        String xml = """
                <project>
                    <properties>
                        <java.version>21</java.version>
                        <spring.version>3.5.16</spring.version>
                    </properties>
                </project>
                """;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(
                new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))
        );

        PropertyExtractor extractor = new PropertyExtractor();

        List<MavenProperty> properties = extractor.extract(document);

        assertEquals(2, properties.size());

        assertEquals("java.version", properties.get(0).getName());
        assertEquals("21", properties.get(0).getValue());

        assertEquals("spring.version", properties.get(1).getName());
        assertEquals("3.5.16", properties.get(1).getValue());
    }
}