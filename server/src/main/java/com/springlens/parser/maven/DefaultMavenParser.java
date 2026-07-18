package com.springlens.parser.maven;

import com.springlens.parser.common.model.ParentInfo;
import com.springlens.parser.maven.extractor.DependencyExtractor;
import com.springlens.parser.maven.extractor.ParentExtractor;
import com.springlens.parser.maven.extractor.PluginExtractor;
import com.springlens.parser.maven.extractor.PropertyExtractor;
import com.springlens.parser.maven.model.MavenDependency;
import com.springlens.parser.maven.model.MavenPlugin;
import com.springlens.parser.maven.model.MavenProjectInfo;
import com.springlens.parser.maven.model.MavenProperty;
import com.springlens.parser.maven.resolver.JavaVersionResolver;
import com.springlens.parser.maven.resolver.SpringBootVersionResolver;
import com.springlens.parser.maven.extractor.ProjectInfoExtractor;
import com.springlens.parser.maven.model.ProjectInfo;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;
import java.util.List;

@Component
public class DefaultMavenParser implements MavenParser {

    private final ParentExtractor parentExtractor;
    private final PropertyExtractor propertyExtractor;
    private final DependencyExtractor dependencyExtractor;
    private final PluginExtractor pluginExtractor;
    private final JavaVersionResolver javaVersionResolver;
    private final SpringBootVersionResolver springBootVersionResolver;
    private final ProjectInfoExtractor projectInfoExtractor;

    public DefaultMavenParser(
            ParentExtractor parentExtractor,
            PropertyExtractor propertyExtractor,
            DependencyExtractor dependencyExtractor,
            PluginExtractor pluginExtractor,
            JavaVersionResolver javaVersionResolver,
            SpringBootVersionResolver springBootVersionResolver,
            ProjectInfoExtractor projectInfoExtractor
    ) {
        this.parentExtractor = parentExtractor;
        this.propertyExtractor = propertyExtractor;
        this.dependencyExtractor = dependencyExtractor;
        this.pluginExtractor = pluginExtractor;
        this.javaVersionResolver = javaVersionResolver;
        this.springBootVersionResolver = springBootVersionResolver;
        this.projectInfoExtractor = projectInfoExtractor;
    }

    @Override
    public MavenProjectInfo parse(Path projectRoot) {

        Path pomFile = projectRoot.resolve("pom.xml");

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(pomFile.toFile());

            document.getDocumentElement().normalize();

            ProjectInfo projectInfo =
                projectInfoExtractor.extract(document);

            ParentInfo parent =
                parentExtractor.extract(document);

            List<MavenProperty> properties =
                propertyExtractor.extract(document);

            List<MavenDependency> dependencies =
                dependencyExtractor.extract(document);

            List<MavenPlugin> plugins =
                pluginExtractor.extract(document);
            return MavenProjectInfo.builder()

        .groupId(projectInfo.getGroupId())
        .artifactId(projectInfo.getArtifactId())
        .version(projectInfo.getVersion())
        .packaging(projectInfo.getPackaging())

        .parentGroupId(
                parent != null ? parent.getGroupId() : null
        )
        .parentArtifactId(
                parent != null ? parent.getArtifactId() : null
        )
        .parentVersion(
                parent != null ? parent.getVersion() : null
        )

        .properties(properties)

        .dependencies(dependencies)

        .plugins(plugins)

        .javaVersion(
                javaVersionResolver.resolve(properties)
        )

        .springBootVersion(
                springBootVersionResolver.resolve(parent)
        )

        .build();
        } catch (Exception ex) {

            throw new IllegalStateException(
                    "Failed to parse pom.xml",
                    ex
            );
        }

    }

}