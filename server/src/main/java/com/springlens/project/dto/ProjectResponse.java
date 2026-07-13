package com.springlens.project.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectResponse(
        UUID id,
        String name,
        String buildTool,
        String language,
        String status,
        LocalDateTime uploadedAt
) {}