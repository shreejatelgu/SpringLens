package com.springlens.parser.maven.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a property defined inside the
 * <properties> section of a pom.xml.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MavenProperty {

    private String name;
    private String value;/* Property value Example: 21*/

}