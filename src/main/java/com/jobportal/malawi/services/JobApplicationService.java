package com.jobportal.malawi.services;

import com.jobportal.malawi.payload.request.dto.JobApplicationRequestDTO;
import com.jobportal.malawi.payload.response.dto.JobApplicationResponseDTO;

import java.util.List;

public interface JobApplicationService {

    JobApplicationResponseDTO applyForJob(JobApplicationRequestDTO jobApplicationRequestDTO);

    JobApplicationResponseDTO updateApplicationStatus(Long applicationId, JobApplicationRequestDTO jobApplicationRequestDTO);

    List<JobApplicationResponseDTO> getApplicationsByJob(Long jobId);

    List<JobApplicationResponseDTO> getApplicationsByUser(Long userId);

    void withdrawApplication(Long applicationId);
}
