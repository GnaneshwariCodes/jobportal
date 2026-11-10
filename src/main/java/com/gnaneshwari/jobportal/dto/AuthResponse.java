package com.gnaneshwari.jobportal.dto;

public record AuthResponse(String token, String email, String role) {
}