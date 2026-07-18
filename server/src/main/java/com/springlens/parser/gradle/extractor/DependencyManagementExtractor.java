package com.springlens.parser.gradle.extractor;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DependencyManagementExtractor {

    private static final Pattern BOM_PATTERN = Pattern.compile(
            "mavenBom\\([\"']([^\"']+)[\"']\\)"
    );

    public List<String> extract(String buildScript) {

        List<String> boms = new ArrayList<>();

        Matcher matcher = BOM_PATTERN.matcher(buildScript);

        while (matcher.find()) {
            boms.add(matcher.group(1));
        }

        return List.copyOf(boms);

    }

}