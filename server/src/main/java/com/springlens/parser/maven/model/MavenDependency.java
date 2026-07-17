package com.springlens.parser.maven.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a dependency declared in pom.xml.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MavenDependency {

    /**
     * Dependency group id.
     */
    private String groupId;

    /**
     * Dependency artifact id.
     */
    private String artifactId;

    /**
     * Dependency version.
     */
    private String version;

    /**
     * Dependency scope.
     * Example: compile, runtime, test
     */
    private String scope;

    /**
     * Whether the dependency is optional.
     */
    private boolean optional;

}