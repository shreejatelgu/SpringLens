package com.springlens.project.dto;

import java.util.UUID;

public record ProjectUploadResponse(
        UUID projectId,
        String projectName,
        String status
) {
}