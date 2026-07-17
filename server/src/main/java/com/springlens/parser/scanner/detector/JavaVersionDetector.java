package com.springlens.parser.scanner.detector;

import com.springlens.parser.maven.model.MavenProjectInfo;
import org.springframework.stereotype.Component;

/**
 * Determines the Java version from parsed Maven information.
 */
@Component
public class JavaVersionDetector {

    /**
     * Detects the Java version.
     *
     * @param projectInfo parsed Maven information
     * @return detected Java version
     */
    public String detect(MavenProjectInfo projectInfo) {

        if (projectInfo == null) {
            return "UNKNOWN";
        }

        String version = projectInfo.getJavaVersion();

        if (version == null || version.isBlank()) {
            return "UNKNOWN";
        }

        return version;
    }
}