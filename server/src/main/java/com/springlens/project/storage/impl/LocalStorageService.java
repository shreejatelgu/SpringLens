package com.springlens.project.storage.impl;

import com.springlens.project.storage.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {

    private static final String WORKSPACE = "workspace";

    @Override
    public Path storeProject(UUID projectId, MultipartFile file) throws IOException {

        Path projectFolder = Paths.get(WORKSPACE, projectId.toString());

        Files.createDirectories(projectFolder);

        Path zipLocation =
                projectFolder.resolve("project.zip");

        Files.copy(
                file.getInputStream(),
                zipLocation,
                StandardCopyOption.REPLACE_EXISTING
        );

        return zipLocation;
    }
}