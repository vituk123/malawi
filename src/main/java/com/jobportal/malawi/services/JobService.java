package com.jobportal.malawi.services;

import com.jobportal.malawi.payload.request.dto.JobRequestDTO;
import com.jobportal.malawi.payload.response.dto.JobResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {

    JobResponseDTO createJob(JobRequestDTO jobRequestDTO);

    JobResponseDTO updateJob(Long jobId, JobRequestDTO jobRequestDTO);

    void deleteJob(Long jobId);

    JobResponseDTO getJobById(Long jobId);

    Page<JobResponseDTO> getAllJobs(Pageable pageable);

    Page<JobResponseDTO> searchJobs(String title, String location, String company, Pageable pageable);

    Page<JobResponseDTO> filterJobs(double minSalary, double maxSalary, String jobType, String location, Pageable pageable);

    List<JobResponseDTO> getJobsByCompany(Long companyId);
}
