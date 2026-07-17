package com.springlens.parser.scanner.detector;

import com.springlens.parser.maven.model.MavenProjectInfo;
import com.springlens.parser.scanner.model.Framework;
import org.springframework.stereotype.Component;

/**
 * Detects the framework used by the project.
 */
@Component
public class FrameworkDetector {

    /**
     * Detects the framework.
     *
     * @param projectInfo parsed Maven information
     * @return detected framework
     */
    public Framework detect(MavenProjectInfo projectInfo) {

        if (projectInfo == null) {
            return Framework.UNKNOWN;
        }

        String version = projectInfo.getVersion();

        if (version == null) {
            return Framework.JAVA;
        }

        // Temporary implementation.
        // Later we'll inspect dependencies and parent POM.
        if ("org.springframework.boot".equals(projectInfo.getParentGroupId())
                && "spring-boot-starter-parent".equals(projectInfo.getParentArtifactId())) {
            return Framework.SPRING_BOOT;
        }
        return Framework.UNKNOWN;
    }

}