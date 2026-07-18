package com.springlens.parser.gradle.extractor;

import com.springlens.parser.gradle.model.ProjectInfo;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ProjectInfoExtractor {

    private static final Pattern GROUP_PATTERN =
            Pattern.compile("^\\s*group\\s*=\\s*['\"]([^'\"]+)['\"]", Pattern.MULTILINE);

    private static final Pattern VERSION_PATTERN =
            Pattern.compile("^\\s*version\\s*=\\s*['\"]([^'\"]+)['\"]", Pattern.MULTILINE);

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^\\s*rootProject\\.name\\s*=\\s*['\"]([^'\"]+)['\"]", Pattern.MULTILINE);

    private static final Pattern DESCRIPTION_PATTERN =
            Pattern.compile("^\\s*description\\s*=\\s*['\"]([^'\"]+)['\"]", Pattern.MULTILINE);

    public ProjectInfo extract(String buildScript) {

        return new ProjectInfo(
                extract(NAME_PATTERN, buildScript),
                extract(GROUP_PATTERN, buildScript),
                extract(VERSION_PATTERN, buildScript),
                extract(DESCRIPTION_PATTERN, buildScript)
        );
    }

    private String extract(Pattern pattern, String buildScript) {

        Matcher matcher = pattern.matcher(buildScript);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        return null;
    }

}