package com.springlens.parser.maven.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
/**
 * Information extracted from a Maven pom.xml file.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MavenProjectInfo {

    @Builder.Default
    private List<MavenProperty> properties = new ArrayList<>();

    @Builder.Default
    private List<MavenDependency> dependencies = new ArrayList<>();

    @Builder.Default
private List<MavenPlugin> plugins = new ArrayList<>();

    @Builder.Default
    private String springBootVersion = null;

    /**
     * Maven groupId.
     */
    private String groupId;

    
    /**
     * Maven artifactId.
     */
    private String artifactId;

    private String parentGroupId;

     private String version;

    private String parentArtifactId;

    private String parentVersion;
    /**
     * Packaging type (jar, war, etc.).
     */
    private String packaging;

    private String javaVersion;

}