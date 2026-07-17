package com.springlens.parser.maven;

import com.springlens.parser.maven.extractor.JavaVersionExtractor;
import com.springlens.parser.maven.model.MavenProjectInfo;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;

/**
 * Default implementation of MavenParser.
 */
@Component
public class DefaultMavenParser implements MavenParser {

    private final JavaVersionExtractor javaVersionExtractor;

    public DefaultMavenParser(JavaVersionExtractor javaVersionExtractor) {
        this.javaVersionExtractor = javaVersionExtractor;
    }

    @Override
    public MavenProjectInfo parse(Path projectRoot) {

        Path pomFile = projectRoot.resolve("pom.xml");

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Prevent XXE attacks
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(pomFile.toFile());

            document.getDocumentElement().normalize();

            Element project = document.getDocumentElement();

            Element parent = getDirectChild(project, "parent");

            return MavenProjectInfo.builder()
                    .groupId(getDirectChildText(project, "groupId"))
                    .artifactId(getDirectChildText(project, "artifactId"))
                    .version(getDirectChildText(project, "version"))
                    .packaging(getDirectChildText(project, "packaging"))

                    .parentGroupId(getDirectChildText(parent, "groupId"))
                    .parentArtifactId(getDirectChildText(parent, "artifactId"))
                    .parentVersion(getDirectChildText(parent, "version"))

                    .javaVersion(javaVersionExtractor.extract(document))
                    .build();

        } catch (Exception ex) {

            throw new IllegalStateException(
                    "Failed to parse pom.xml",
                    ex
            );
        }
    }

    /**
     * Returns the first direct child element having the given tag name.
     */
    private Element getDirectChild(Element parent, String tagName) {

        if (parent == null) {
            return null;
        }

        NodeList children = parent.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {

            Node node = children.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE
                    && tagName.equals(node.getNodeName())) {

                return (Element) node;
            }
        }

        return null;
    }

    /**
     * Returns the text content of a direct child element.
     */
    private String getDirectChildText(Element parent, String tagName) {

        Element child = getDirectChild(parent, tagName);

        if (child == null) {
            return null;
        }

        String value = child.getTextContent();

        return value == null ? null : value.trim();
    }
}