package com.springlens.parser.maven.extractor;

import com.springlens.parser.maven.model.MavenDependency;
import com.springlens.parser.maven.util.XmlUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

@Component
public class DependencyExtractor {

    public List<MavenDependency> extract(Document document) {

        List<MavenDependency> dependencies = new ArrayList<>();

        NodeList dependencyNodes = document.getElementsByTagName("dependency");

        for (int i = 0; i < dependencyNodes.getLength(); i++) {

            Element dependency = (Element) dependencyNodes.item(i);

            dependencies.add(

                    MavenDependency.builder()

                            .groupId(XmlUtils.getDirectChildText(dependency, "groupId"))
                            .artifactId(XmlUtils.getDirectChildText(dependency, "artifactId"))
                            .version(XmlUtils.getDirectChildText(dependency, "version"))
                            .scope(XmlUtils.getDirectChildText(dependency, "scope"))
                            .optional(Boolean.parseBoolean(
                                    XmlUtils.getDirectChildText(dependency, "optional")
                            ))
                            .build()

            );
        }

        return dependencies;
    }

}