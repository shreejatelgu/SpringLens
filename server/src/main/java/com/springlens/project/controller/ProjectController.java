package com.springlens.project.controller;

import com.springlens.common.response.ApiResponse;
import com.springlens.project.dto.ProjectUploadResponse;
import com.springlens.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    

@PostMapping(
        value = "/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
)
public ApiResponse<ProjectUploadResponse> uploadProject(
        @RequestParam("file") MultipartFile file
) {

    ProjectUploadResponse response =
            projectService.uploadProject(file);

    return ApiResponse.success(
            "Project uploaded successfully.",
            response
    );
}

}