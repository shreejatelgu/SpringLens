package com.springlens.parser.gradle.model;

import java.util.List;

import com.springlens.parser.common.model.ParentInfo;

public record GradleProjectInfo(
        ProjectInfo projectInfo,
        ParentInfo parentInfo,
        String springBootVersion,
        List<GradleProperty> properties,
        List<GradleDependency> dependencies,
        List<GradlePlugin> plugins,
        String javaVersion,
        List<String> importedBoms,
        List<String> modules
) {}                
