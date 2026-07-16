package com.springlens.project.enums;

/*
 * Represents every stage
 * a project goes through inside SpringLens.
 */
public enum ProjectStatus {

    /*
     * User uploaded ZIP successfully.
     */
    UPLOADED,

    /*
     * ZIP extraction in progress.
     */
    EXTRACTING,

    /*
     * Spring project is being parsed.
     */
    PARSING,

    /*
     * AI engine is analysing architecture.
     */
    ANALYZING,

    /*
     * Everything completed successfully.
     */
    COMPLETED,

    /*
     * Something failed during processing.
     */
    FAILED

}