package com.springlens.parser.maven.extractor;

import com.springlens.parser.common.model.ParentInfo;
import com.springlens.parser.maven.util.XmlUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Component
public class ParentExtractor {

    public ParentInfo extract(Document document) {

        Element project = document.getDocumentElement();

        Element parent = XmlUtils.getDirectChild(project, "parent");

        if (parent == null) {
            return null;
        }

        return ParentInfo.builder()
                .groupId(XmlUtils.getDirectChildText(parent, "groupId"))
                .artifactId(XmlUtils.getDirectChildText(parent, "artifactId"))
                .version(XmlUtils.getDirectChildText(parent, "version"))
                .build();
    }

}