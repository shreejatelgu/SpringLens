package com.springlens.common.controller;

import com.springlens.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public ApiResponse<Map<String, String>> health() {

        return ApiResponse.success(
                "SpringLens is running successfully.",
                Map.of(
                        "service", "SpringLens",
                        "version", "1.0.0",
                        "status", "UP"
                )
        );
    }
}