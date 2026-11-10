package com.gnaneshwari.jobportal.service;

import com.gnaneshwari.jobportal.exception.ResourceNotFoundException;
import com.gnaneshwari.jobportal.model.Job;
import com.gnaneshwari.jobportal.repository.JobRepository;
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
}