package com.springlens.parser.maven.extractor;

import com.springlens.parser.maven.model.MavenProperty;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

@Component
public class PropertyExtractor {

    public List<MavenProperty> extract(Document document) {

        List<MavenProperty> properties = new ArrayList<>();

        NodeList propertyNodes = document.getElementsByTagName("properties");

        if (propertyNodes.getLength() == 0) {
            return properties;
        }

        Element propertiesElement = (Element) propertyNodes.item(0);

        NodeList children = propertiesElement.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {

            Node node = children.item(i);

            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            properties.add(
                    MavenProperty.builder()
                            .name(node.getNodeName())
                            .value(node.getTextContent().trim())
                            .build()
            );
        }

        return properties;
    }

}