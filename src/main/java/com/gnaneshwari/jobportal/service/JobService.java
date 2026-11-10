package com.gnaneshwari.jobportal.service;

import com.gnaneshwari.jobportal.dto.PageResponse;
import com.gnaneshwari.jobportal.exception.ResourceNotFoundException;
import com.gnaneshwari.jobportal.model.Job;
import com.gnaneshwari.jobportal.repository.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    // Constructor injection (preferred over @Autowired on fields)
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id " + id));
    }

    public Job updateJob(Long id, Job updated) {
        Job job = getJobById(id);
        job.setTitle(updated.getTitle());
        job.setCompany(updated.getCompany());
        job.setLocation(updated.getLocation());
        job.setDescription(updated.getDescription());
        job.setSalary(updated.getSalary());
        return jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        jobRepository.delete(getJobById(id));
    }

    public List<Job> searchByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title);
    }

    public PageResponse<Job> search(String title, String location, Double minSalary, int page, int size) {
        Pageable pageable = PageRequest.of(page, Math.min(size, 50), Sort.by("id").descending());
        Page<Job> result = jobRepository.search(
                title == null ? "" : title,
                location == null ? "" : location,
                minSalary == null ? 0.0 : minSalary,
                pageable);
        return new PageResponse<>(result.getContent(), result.getNumber(), result.getSize(),
                result.getTotalElements(), result.getTotalPages());
    }
}