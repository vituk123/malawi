package com.jobportal.malawi.services.impl;

import com.jobportal.malawi.exception.ResourceNotFoundException;
import com.jobportal.malawi.models.*;
import com.jobportal.malawi.payload.request.dto.JobRequestDTO;
import com.jobportal.malawi.payload.response.dto.CompanyResponseDTO;
import com.jobportal.malawi.payload.response.dto.JobResponseDTO;
import com.jobportal.malawi.repository.CompanyRepository;
import com.jobportal.malawi.repository.JobRepository;
import com.jobportal.malawi.services.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public JobResponseDTO createJob(JobRequestDTO jobRequestDTO) {
        Company company = companyRepository.findById(jobRequestDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        // Add authorization logic here to check if the user is the company owner or admin

        if (jobRepository.findByCompanyId(company.getId()).size() >= 10) {
            throw new IllegalArgumentException("You have reached the maximum number of active jobs for your company");
        }

        Job job = modelMapper.map(jobRequestDTO, Job.class);
        job.setCompany(company);
        job.setCreatedAt(LocalDateTime.now().plusDays(90)); // Auto-expire after 90 days
        job = jobRepository.save(job);
        return modelMapper.map(job, JobResponseDTO.class);
    }

    @Override
    public JobResponseDTO updateJob(Long jobId, JobRequestDTO jobRequestDTO) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        // Add authorization logic here to check if the user is the company owner or admin

        modelMapper.map(jobRequestDTO, job);
        job = jobRepository.save(job);
        return modelMapper.map(job, JobResponseDTO.class);
    }

    @Override
    public void deleteJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        // Add authorization logic here to check if the user is the company owner or admin

        job.setStatus(JobStatus.INACTIVE);
        jobRepository.save(job);
    }

    @Override
    public JobResponseDTO getJobById(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        JobResponseDTO jobResponseDTO = modelMapper.map(job, JobResponseDTO.class);
        jobResponseDTO.setCompany(modelMapper.map(job.getCompany(), CompanyResponseDTO.class));
        return jobResponseDTO;
    }

    @Override
    public Page<JobResponseDTO> getAllJobs(Pageable pageable) {
        return jobRepository.findAll(pageable)
                .map(job -> modelMapper.map(job, JobResponseDTO.class));
    }

    @Override
    public Page<JobResponseDTO> searchJobs(String title, String location, String company, Pageable pageable) {
        return jobRepository.findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCaseAndCompany_NameContainingIgnoreCase(title, location, company, pageable)
                .map(job -> modelMapper.map(job, JobResponseDTO.class));
    }

    @Override
    public Page<JobResponseDTO> filterJobs(double minSalary, double maxSalary, String jobType, String location, Pageable pageable) {
        return jobRepository.findBySalaryBetweenAndJobTypeAndLocationContainingIgnoreCase(minSalary, maxSalary, JobType.valueOf(jobType), location, pageable)
                .map(job -> modelMapper.map(job, JobResponseDTO.class));
    }

    @Override
    public List<JobResponseDTO> getJobsByCompany(Long companyId) {
        return jobRepository.findByCompanyId(companyId).stream()
                .map(job -> modelMapper.map(job, JobResponseDTO.class))
                .collect(Collectors.toList());
    }
}
