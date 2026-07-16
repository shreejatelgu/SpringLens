package com.springlens.project.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public interface StorageService {

    Path storeProject(UUID projectId, MultipartFile file) throws IOException;

}