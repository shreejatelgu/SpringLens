package com.springlens.parser.maven.extractor;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Extracts the Java version from a Maven POM.
 *
 * Order of precedence:
 *
 * 1. <properties><java.version>
 * 2. <maven.compiler.release>
 * 3. <release>
 * 4. <source>
 */
@Component
public class JavaVersionExtractor {

    public String extract(Document document) {

        String version;

        version = firstTag(document, "java.version");
        if (version != null) {
            return version;
        }

        version = firstTag(document, "maven.compiler.release");
        if (version != null) {
            return version;
        }

        version = firstTag(document, "release");
        if (version != null) {
            return version;
        }

        version = firstTag(document, "source");
        if (version != null) {
            return version;
        }

        return "UNKNOWN";
    }

    private String firstTag(Document document, String tagName) {

        NodeList nodes = document.getElementsByTagName(tagName);

        if (nodes.getLength() == 0) {
            return null;
        }

        String value = nodes.item(0).getTextContent();

        return value == null ? null : value.trim();
    }
}