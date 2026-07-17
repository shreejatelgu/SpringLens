package com.springlens.parser.maven.extractor;

import com.springlens.parser.maven.model.ProjectInfo;
import com.springlens.parser.maven.util.XmlUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Component
public class ProjectInfoExtractor {

    public ProjectInfo extract(Document document) {

        Element project = document.getDocumentElement();

        return ProjectInfo.builder()
                .groupId(XmlUtils.getDirectChildText(project, "groupId"))
                .artifactId(XmlUtils.getDirectChildText(project, "artifactId"))
                .version(XmlUtils.getDirectChildText(project, "version"))
                .packaging(XmlUtils.getDirectChildText(project, "packaging"))
                .build();
    }
}