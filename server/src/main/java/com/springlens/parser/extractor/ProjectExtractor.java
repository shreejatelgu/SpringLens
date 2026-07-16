package com.springlens.parser.extractor;

import java.io.IOException;
import java.nio.file.Path;

public interface ProjectExtractor {

    /**
     * Extracts an uploaded project archive.
     *
     * @param zipFile Path to the uploaded ZIP file.
     * @param destination Directory where the project should be extracted.
     * @throws IOException if extraction fails.
     */
    void extract(Path zipFile, Path destination) throws IOException;

}