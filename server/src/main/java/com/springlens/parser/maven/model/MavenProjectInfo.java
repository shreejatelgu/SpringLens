package com.springlens.parser.maven.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Information extracted from a Maven pom.xml file.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MavenProjectInfo {

    /**
     * Maven groupId.
     */
    private String groupId;

    /**
     * Maven artifactId.
     */
    private String artifactId;

    /**
     * Project version.
     */
    private String version;

    /**
     * Packaging type (jar, war, etc.).
     */
    private String packaging;

}