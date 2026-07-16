package com.springlens.parser.scanner.model;

import java.nio.file.Path;

/**
 * Metadata discovered while scanning a Spring Boot project.
 *
 * This object is produced by the scanner and consumed by
 * subsequent parsers and analyzers.
 * 
 */

public class ProjectMetadata {

    private Path projectRoot;

    private BuildTool buildTool;

    private Framework framework;

    private String javaVersion;

    private String springBootVersion;

    private ProjectLayout projectLayout;

    private String basePackage;

    private Path mainApplicationClass;

    private ScanStatus status;

    public Path getProjectRoot() {
        return projectRoot;
    }
    
    public ProjectLayout getProjectLayout() {
        return projectLayout;
    }

    public void setProjectLayout(ProjectLayout projectLayout) {
        this.projectLayout = projectLayout;
    }
    public void setProjectRoot(Path projectRoot) {
        this.projectRoot = projectRoot;
    }

    public BuildTool getBuildTool() {
        return buildTool;
    }

    public void setBuildTool(BuildTool buildTool) {
        this.buildTool = buildTool;
    }

    public Framework getFramework() {
        return framework;
    }

    public void setFramework(Framework framework) {
        this.framework = framework;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getSpringBootVersion() {
        return springBootVersion;
    }

    public void setSpringBootVersion(String springBootVersion) {
        this.springBootVersion = springBootVersion;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public Path getMainApplicationClass() {
        return mainApplicationClass;
    }

    public void setMainApplicationClass(Path mainApplicationClass) {
        this.mainApplicationClass = mainApplicationClass;
    }

    public ScanStatus getStatus() {
        return status;
    }

    public void setStatus(ScanStatus status) {
        this.status = status;
    }
}