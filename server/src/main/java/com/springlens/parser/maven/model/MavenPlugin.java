package com.springlens.parser.maven.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MavenPlugin {

    private String groupId;

    private String artifactId;

    private String version;

}