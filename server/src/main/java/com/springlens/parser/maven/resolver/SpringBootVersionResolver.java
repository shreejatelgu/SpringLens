package com.springlens.parser.maven.resolver;

import org.springframework.stereotype.Component;

import com.springlens.parser.common.model.ParentInfo;

@Component
public class SpringBootVersionResolver {

    public String resolve(ParentInfo parentInfo) {

        if (parentInfo == null) {
            return null;
        }

        if ("org.springframework.boot".equals(parentInfo.getGroupId())
                && "spring-boot-starter-parent".equals(parentInfo.getArtifactId())) {

            return parentInfo.getVersion();
        }

        return null;
    }

}