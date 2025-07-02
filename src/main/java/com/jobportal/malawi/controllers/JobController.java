package com.jobportal.malawi.controllers;

import com.jobportal.malawi.payload.request.dto.JobRequestDTO;
import com.jobportal.malawi.payload.response.dto.JobResponseDTO;
import com.jobportal.malawi.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
    public ResponseEntity<JobResponseDTO> createJob(@RequestBody JobRequestDTO jobRequestDTO) {
        return new ResponseEntity<>(jobService.createJob(jobRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{jobId}")
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
    public ResponseEntity<JobResponseDTO> updateJob(@PathVariable Long jobId, @RequestBody JobRequestDTO jobRequestDTO) {
        return ResponseEntity.ok(jobService.updateJob(jobId, jobRequestDTO));
    }

    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteJob(@PathVariable Long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponseDTO> getJobById(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobService.getJobById(jobId));
    }

    @GetMapping
    public ResponseEntity<Page<JobResponseDTO>> getAllJobs(Pageable pageable) {
        return ResponseEntity.ok(jobService.getAllJobs(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<JobResponseDTO>> searchJobs(@RequestParam(required = false) String title, @RequestParam(required = false) String location, @RequestParam(required = false) String company, Pageable pageable) {
        return ResponseEntity.ok(jobService.searchJobs(title, location, company, pageable));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<JobResponseDTO>> filterJobs(@RequestParam double minSalary, @RequestParam double maxSalary, @RequestParam String jobType, @RequestParam String location, Pageable pageable) {
        return ResponseEntity.ok(jobService.filterJobs(minSalary, maxSalary, jobType, location, pageable));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobResponseDTO>> getJobsByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(jobService.getJobsByCompany(companyId));
    }
}
