package com.springlens.parser.maven.resolver;

import com.springlens.parser.maven.model.MavenProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JavaVersionResolver {

    public String resolve(List<MavenProperty> properties) {

        if (properties == null) {
            return null;
        }

        for (MavenProperty property : properties) {

            if ("java.version".equals(property.getName())) {
                return property.getValue();
            }
        }

        for (MavenProperty property : properties) {

            if ("maven.compiler.release".equals(property.getName())) {
                return property.getValue();
            }
        }

        for (MavenProperty property : properties) {

            if ("maven.compiler.source".equals(property.getName())) {
                return property.getValue();
            }
        }

        for (MavenProperty property : properties) {

            if ("maven.compiler.target".equals(property.getName())) {
                return property.getValue();
            }
        }

        return null;
    }

}