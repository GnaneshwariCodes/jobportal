package com.gnaneshwari.jobportal.controller;

import com.gnaneshwari.jobportal.dto.PageResponse;
import com.gnaneshwari.jobportal.model.Job;
import com.gnaneshwari.jobportal.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<Job> create(@Valid @RequestBody Job job) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(job));
    }

    @GetMapping
    public PageResponse<Job> getAll(@RequestParam(required = false) String title,
                                    @RequestParam(required = false) String location,
                                    @RequestParam(required = false) Double minSalary,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size) {
        return jobService.search(title, location, minSalary, page, size);
    }

    @GetMapping("/{id}")
    public Job getOne(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @PutMapping("/{id}")
    public Job update(@PathVariable Long id, @Valid @RequestBody Job job) {
        return jobService.updateJob(id, job);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}