package com.springlens.project.validator;

import com.springlens.common.exception.badRequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProjectValidator {

    private static final long MAX_FILE_SIZE = 100 * 1024 * 1024;

    public void validate(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new badRequestException("No file was uploaded.");
        }

        String filename = file.getOriginalFilename();

        if (filename == null || !filename.toLowerCase().endsWith(".zip")) {
            throw new badRequestException("Only ZIP files are supported.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new badRequestException("Maximum upload size is 100 MB.");
        }
    }
}