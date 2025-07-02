package com.jobportal.malawi.controllers;

import com.jobportal.malawi.payload.request.dto.JobApplicationRequestDTO;
import com.jobportal.malawi.payload.response.dto.JobApplicationResponseDTO;
import com.jobportal.malawi.services.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @Autowired
    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<JobApplicationResponseDTO> applyForJob(@RequestBody JobApplicationRequestDTO jobApplicationRequestDTO) {
        return new ResponseEntity<>(jobApplicationService.applyForJob(jobApplicationRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{applicationId}")
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
    public ResponseEntity<JobApplicationResponseDTO> updateApplicationStatus(@PathVariable Long applicationId, @RequestBody JobApplicationRequestDTO jobApplicationRequestDTO) {
        return ResponseEntity.ok(jobApplicationService.updateApplicationStatus(applicationId, jobApplicationRequestDTO));
    }

    @GetMapping("/job/{jobId}")
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
    public ResponseEntity<List<JobApplicationResponseDTO>> getApplicationsByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByJob(jobId));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('JOB_SEEKER') or hasRole('ADMIN')")
    public ResponseEntity<List<JobApplicationResponseDTO>> getApplicationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByUser(userId));
    }

    @DeleteMapping("/{applicationId}")
    @PreAuthorize("hasRole('JOB_SEEKER') or hasRole('ADMIN')")
    public ResponseEntity<Void> withdrawApplication(@PathVariable Long applicationId) {
        jobApplicationService.withdrawApplication(applicationId);
        return ResponseEntity.noContent().build();
    }
}
