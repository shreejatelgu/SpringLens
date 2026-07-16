package com.springlens.project.service;

import com.springlens.project.dto.ProjectUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectService {

    ProjectUploadResponse uploadProject(MultipartFile file);

}