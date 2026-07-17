package com.springlens.parser.maven.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Basic Maven project information.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInfo {

    private String groupId;

    private String artifactId;

    private String version;

    private String packaging;

}