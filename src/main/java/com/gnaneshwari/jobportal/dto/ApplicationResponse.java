package com.gnaneshwari.jobportal.dto;

import java.time.LocalDateTime;

public record ApplicationResponse(Long id, Long jobId, String jobTitle,
                                  String applicantName, String applicantEmail,
                                  LocalDateTime appliedAt) {
}