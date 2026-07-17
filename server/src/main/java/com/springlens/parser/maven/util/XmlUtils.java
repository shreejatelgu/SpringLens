package com.springlens.parser.maven.util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class XmlUtils {

    private XmlUtils() {
    }

    public static Element getDirectChild(Element parent, String tagName) {

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

    public static String getDirectChildText(Element parent, String tagName) {

        Element child = getDirectChild(parent, tagName);

        if (child == null) {
            return null;
        }

        String value = child.getTextContent();

        return value == null ? null : value.trim();
    }

}