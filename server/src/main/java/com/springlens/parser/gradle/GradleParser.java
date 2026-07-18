package com.springlens.parser.gradle;

import com.springlens.parser.gradle.model.GradleProjectInfo;

import java.nio.file.Path;

public interface GradleParser {

    GradleProjectInfo parse(Path buildScript);

}