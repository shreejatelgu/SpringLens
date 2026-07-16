package com.springlens.parser.maven;

import com.springlens.parser.maven.model.MavenProjectInfo;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;

/**
 * Default implementation of MavenParser.
 */
@Component
public class DefaultMavenParser implements MavenParser {

    @Override
    public MavenProjectInfo parse(Path projectRoot) {

        Path pomFile = projectRoot.resolve("pom.xml");

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Security against XXE attacks
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(pomFile.toFile());

            document.getDocumentElement().normalize();

            Element root = document.getDocumentElement();

            return MavenProjectInfo.builder()
                    .groupId(text(root, "groupId"))
                    .artifactId(text(root, "artifactId"))
                    .version(text(root, "version"))
                    .packaging(text(root, "packaging"))
                    .build();

        } catch (Exception ex) {

            throw new IllegalStateException(
                    "Failed to parse pom.xml",
                    ex
            );
        }
    }

    private String text(Element root, String tag) {

        if (root.getElementsByTagName(tag).getLength() == 0) {
            return null;
        }

        return root
                .getElementsByTagName(tag)
                .item(0)
                .getTextContent()
                .trim();
    }

}