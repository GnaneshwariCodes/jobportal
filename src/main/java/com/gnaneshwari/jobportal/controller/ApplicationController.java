package com.gnaneshwari.jobportal.controller;

import com.gnaneshwari.jobportal.dto.ApplicationResponse;
import com.gnaneshwari.jobportal.service.ApplicationService;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/jobs/{jobId}/apply")
    public ResponseEntity<ApplicationResponse> apply(@PathVariable Long jobId, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(applicationService.apply(jobId, auth.getName()));
    }

    @GetMapping("/applications/my")
    public List<ApplicationResponse> mine(Authentication auth) {
        return applicationService.myApplications(auth.getName());
    }

    @GetMapping("/jobs/{jobId}/applicants")
    public List<ApplicationResponse> applicants(@PathVariable Long jobId) {
        return applicationService.applicantsForJob(jobId);
    }
}