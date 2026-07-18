package com.springlens.parser.gradle.extractor;

import com.springlens.parser.common.model.ParentInfo;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParentExtractor {

    private static final String SPRING_BOOT_PLUGIN =
            "org.springframework.boot";

    private static final Pattern KOTLIN_PLUGIN = Pattern.compile(
            "id\\(\"org\\.springframework\\.boot\"\\)\\s*version\\s*\"([^\"]+)\""
    );

    private static final Pattern GROOVY_PLUGIN = Pattern.compile(
            "id\\s+'org\\.springframework\\.boot'\\s*version\\s+'([^']+)'"
    );

    public ParentInfo extract(String buildScript) {

        Matcher kotlinMatcher = KOTLIN_PLUGIN.matcher(buildScript);

        if (kotlinMatcher.find()) {
            return new ParentInfo(
                    SPRING_BOOT_PLUGIN,
                    null,
                    kotlinMatcher.group(1)
            );
        }

        Matcher groovyMatcher = GROOVY_PLUGIN.matcher(buildScript);

        if (groovyMatcher.find()) {
            return new ParentInfo(
                    SPRING_BOOT_PLUGIN,
                    null,
                    groovyMatcher.group(1)
            );
        }

        return null;
    }

}