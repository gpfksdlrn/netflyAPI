package com.api.project.domain.health.contoller;

import com.api.project.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthController {
    @GetMapping("/v1/api/health")
    public CommonResponse healthCheck() {
        return CommonResponse.responseSuccess();
    }
}
