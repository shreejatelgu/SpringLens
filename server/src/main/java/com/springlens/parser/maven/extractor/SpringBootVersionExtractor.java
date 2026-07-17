package com.springlens.parser.maven.extractor;

import org.springframework.stereotype.Component;

import com.springlens.parser.maven.model.MavenProjectInfo;

@Component
public class SpringBootVersionExtractor {

    public String extract(MavenProjectInfo projectInfo) {

        if (projectInfo == null) {
            return null;
        }

        if ("org.springframework.boot".equals(projectInfo.getParentGroupId())
                && "spring-boot-starter-parent".equals(projectInfo.getParentArtifactId())) {

            return projectInfo.getParentVersion();
        }

        return null;
    }

}
