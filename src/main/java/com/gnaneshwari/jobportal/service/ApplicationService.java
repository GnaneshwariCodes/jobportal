package com.gnaneshwari.jobportal.service;

import com.gnaneshwari.jobportal.dto.ApplicationResponse;
import com.gnaneshwari.jobportal.exception.DuplicateResourceException;
import com.gnaneshwari.jobportal.exception.ResourceNotFoundException;
import com.gnaneshwari.jobportal.model.*;
import com.gnaneshwari.jobportal.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              JobRepository jobRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ApplicationResponse apply(Long jobId, String email) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id " + jobId));
        User user = userRepository.findByEmail(email).orElseThrow();

        if (applicationRepository.existsByJobIdAndApplicantId(jobId, user.getId())) {
            throw new DuplicateResourceException("You already applied to this job");
        }
        Application app = new Application();
        app.setJob(job);
        app.setApplicant(user);
        return toResponse(applicationRepository.save(app));
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> myApplications(String email) {
        return applicationRepository.findByApplicantEmail(email).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> applicantsForJob(Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("Job not found with id " + jobId);
        }
        return applicationRepository.findByJobId(jobId).stream().map(this::toResponse).toList();
    }

    private ApplicationResponse toResponse(Application a) {
        return new ApplicationResponse(a.getId(), a.getJob().getId(), a.getJob().getTitle(),
                a.getApplicant().getName(), a.getApplicant().getEmail(), a.getAppliedAt());
    }
}