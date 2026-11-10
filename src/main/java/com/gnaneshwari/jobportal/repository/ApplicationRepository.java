package com.gnaneshwari.jobportal.repository;

import com.gnaneshwari.jobportal.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByJobIdAndApplicantId(Long jobId, Long userId);
    List<Application> findByApplicantEmail(String email);
    List<Application> findByJobId(Long jobId);
}