package com.springlens.project.service.impl;

import com.springlens.common.exception.ProjectImportException;
import com.springlens.project.dto.ProjectUploadResponse;
import com.springlens.project.entity.Project;
import com.springlens.project.enums.ProjectStatus;
import com.springlens.project.repository.ProjectRepository;
import com.springlens.project.service.ProjectService;
import com.springlens.project.storage.StorageService;
import com.springlens.project.validator.ProjectValidator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.springlens.parser.extractor.ProjectExtractor;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectValidator projectValidator;
    private final StorageService storageService;
    private final ProjectExtractor projectExtractor;

    @Override
    public ProjectUploadResponse uploadProject(MultipartFile file) {

        // Step 1
        projectValidator.validate(file);

        // Step 2
        Project project = Project.builder()
                .name(file.getOriginalFilename())
                .status(ProjectStatus.UPLOADED)
                .uploadedAt(LocalDateTime.now())
                .build();

        // Step 3
        // save project
        project = projectRepository.save(project);

        Path storedPath;

        try {
            storedPath = storageService.storeProject(
                    project.getId(),
                    file);
                    /*
 * Extract the uploaded ZIP into
 * workspace/<project-id>/extracted
 */

                Path extractionDirectory =
                        storedPath.getParent().resolve("extracted");

                projectExtractor.extract(
                        storedPath,
                        extractionDirectory
                );
        } catch (IOException e) {
            throw new ProjectImportException(
                    "Unable to store uploaded project: " + e.getMessage());
        }

        // Step 4
        // store zip

        // Step 5
        // update upload path
        project.setUploadPath(
                storedPath.toString());

        projectRepository.save(project);

        // Step 6
        // return response
        return new ProjectUploadResponse(
                project.getId(),
                project.getName(),
                project.getStatus().name()
        );

    }
}   
