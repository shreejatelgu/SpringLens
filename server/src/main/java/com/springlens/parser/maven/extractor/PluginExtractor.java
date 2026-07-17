package com.springlens.parser.maven.extractor;

import com.springlens.parser.maven.model.MavenPlugin;
import com.springlens.parser.maven.util.XmlUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

@Component
public class PluginExtractor {

    public List<MavenPlugin> extract(Document document) {

        List<MavenPlugin> plugins = new ArrayList<>();

        NodeList pluginNodes = document.getElementsByTagName("plugin");

        for (int i = 0; i < pluginNodes.getLength(); i++) {

            Element plugin = (Element) pluginNodes.item(i);

            plugins.add(

                    MavenPlugin.builder()

                            .groupId(XmlUtils.getDirectChildText(plugin, "groupId"))
                            .artifactId(XmlUtils.getDirectChildText(plugin, "artifactId"))
                            .version(XmlUtils.getDirectChildText(plugin, "version"))
                            .build()

            );
        }

        return plugins;
    }

}