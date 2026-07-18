package com.springlens.parser.gradle;

import com.springlens.parser.common.model.ParentInfo;
import com.springlens.parser.gradle.exception.GradleParseException;
import com.springlens.parser.gradle.extractor.DependencyExtractor;
import com.springlens.parser.gradle.extractor.DependencyManagementExtractor;
import com.springlens.parser.gradle.extractor.ParentExtractor;
import com.springlens.parser.gradle.extractor.PluginExtractor;
import com.springlens.parser.gradle.extractor.ProjectInfoExtractor;
import com.springlens.parser.gradle.extractor.PropertyExtractor;
import com.springlens.parser.gradle.model.GradleDependency;
import com.springlens.parser.gradle.model.GradlePlugin;
import com.springlens.parser.gradle.model.GradleProjectInfo;
import com.springlens.parser.gradle.model.GradleProperty;
import com.springlens.parser.gradle.model.ProjectInfo;
import com.springlens.parser.gradle.resolver.BuildScriptResolver;
import com.springlens.parser.gradle.resolver.JavaVersionResolver;
import com.springlens.parser.gradle.resolver.SpringBootVersionResolver;
import com.springlens.parser.gradle.settings.SettingsGradleParser;
import com.springlens.parser.gradle.settings.model.GradleSettings;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class DefaultGradleParser implements GradleParser {

    private final BuildScriptResolver buildScriptResolver;
    private final ProjectInfoExtractor projectInfoExtractor;
    private final ParentExtractor parentExtractor;
    private final PropertyExtractor propertyExtractor;
    private final DependencyExtractor dependencyExtractor;
    private final PluginExtractor pluginExtractor;
    private final JavaVersionResolver javaVersionResolver;
    private final SpringBootVersionResolver springBootVersionResolver;
    private final DependencyManagementExtractor dependencyManagementExtractor;
    private final SettingsGradleParser settingsGradleParser;


    public DefaultGradleParser(
            BuildScriptResolver buildScriptResolver,
            ProjectInfoExtractor projectInfoExtractor,
            ParentExtractor parentExtractor,
            PropertyExtractor propertyExtractor,
            DependencyExtractor dependencyExtractor,
            PluginExtractor pluginExtractor,
            JavaVersionResolver javaVersionResolver,
            SpringBootVersionResolver springBootVersionResolver,
            DependencyManagementExtractor dependencyManagementExtractor,
            SettingsGradleParser settingsGradleParser
    ) {
        this.buildScriptResolver = buildScriptResolver;
        this.projectInfoExtractor = projectInfoExtractor;
        this.parentExtractor = parentExtractor;
        this.propertyExtractor = propertyExtractor;
        this.dependencyExtractor = dependencyExtractor;
        this.pluginExtractor = pluginExtractor;
        this.javaVersionResolver = javaVersionResolver;
        this.springBootVersionResolver = springBootVersionResolver;
        this.dependencyManagementExtractor = dependencyManagementExtractor;
        this.settingsGradleParser = settingsGradleParser;
    }

    @Override
    public GradleProjectInfo parse(Path projectRoot) {

        Path buildScript = buildScriptResolver.resolve(projectRoot);

        try {

            String script = Files.readString(buildScript);

            ProjectInfo projectInfo =
                    projectInfoExtractor.extract(script);

            ParentInfo parentInfo =
                    parentExtractor.extract(script);

            List<GradleProperty> properties =
                    propertyExtractor.extract(script);

            List<GradleDependency> dependencies =
                    dependencyExtractor.extract(script);

            List<GradlePlugin> plugins =
                    pluginExtractor.extract(script);

            String springBootVersion =
                    springBootVersionResolver.resolve(plugins);

            String javaVersion =
                    javaVersionResolver.resolve(properties);

            List<String> importedBoms =
                    dependencyManagementExtractor.extract(script);

            GradleSettings settings =
                    settingsGradleParser.parse(projectRoot);

            return new GradleProjectInfo(
                    projectInfo,
                    parentInfo,
                    springBootVersion,
                    properties,
                    dependencies,
                    plugins,
                    javaVersion,
                    importedBoms,
                    settings.includedModules()
            );

        } catch (IOException ex) {

            throw new GradleParseException(
                    "Failed to parse Gradle build script.",
                    ex
            );

        }
    }
}