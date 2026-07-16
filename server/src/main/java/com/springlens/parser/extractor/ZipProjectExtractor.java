package com.springlens.parser.extractor;

import com.springlens.parser.exception.ExtractionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@Component
public class ZipProjectExtractor implements ProjectExtractor {

    @Override
    public void extract(Path zipFile, Path destination) throws IOException {

        /*
         * Create destination directory if it
         * doesn't already exist.
         */
        Files.createDirectories(destination);

        try (ZipInputStream zipInputStream =
                     new ZipInputStream(Files.newInputStream(zipFile))) {

            ZipEntry entry;

            while ((entry = zipInputStream.getNextEntry()) != null) {

                /*
                 * Resolve the target path.
                 */
                Path targetPath =
                        destination.resolve(entry.getName()).normalize();

                /*
                 * ZIP Slip Protection
                 *
                 * Prevents malicious ZIP files
                 * from writing outside the destination.
                 */
                if (!targetPath.startsWith(destination)) {
                    throw new ExtractionException(
                            "Invalid ZIP entry: " + entry.getName()
                    );
                }

                if (entry.isDirectory()) {

                    Files.createDirectories(targetPath);

                } else {

                    Files.createDirectories(targetPath.getParent());

                    

                        Files.copy(
                                zipInputStream,
                                targetPath,
                                StandardCopyOption.REPLACE_EXISTING
                        );

                    

                }

                zipInputStream.closeEntry();

            }

            log.info("Project extracted successfully to {}", destination);

        } 
        }

    

}