package com.jobportal.malawi.services.impl;

import com.jobportal.malawi.exception.ResourceNotFoundException;
import com.jobportal.malawi.models.*;
import com.jobportal.malawi.payload.request.dto.JobApplicationRequestDTO;
import com.jobportal.malawi.payload.response.dto.JobApplicationResponseDTO;
import com.jobportal.malawi.repository.JobApplicationRepository;
import com.jobportal.malawi.repository.JobRepository;
import com.jobportal.malawi.repository.UserProfileRepository;
import com.jobportal.malawi.repository.UserRepository;
import com.jobportal.malawi.services.JobApplicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository, JobRepository jobRepository, UserRepository userRepository, UserProfileRepository userProfileRepository, ModelMapper modelMapper) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public JobApplicationResponseDTO applyForJob(JobApplicationRequestDTO jobApplicationRequestDTO) {
        User user = userRepository.findById(jobApplicationRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));

        if (userProfile.getSkills() == null || userProfile.getSkills().isEmpty()) {
            throw new IllegalArgumentException("Please complete your profile before applying for a job");
        }

        Job job = jobRepository.findById(jobApplicationRequestDTO.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (job.getStatus() == JobStatus.INACTIVE) {
            throw new IllegalArgumentException("This job is no longer active");
        }

        if (job.getCompany().getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You cannot apply to your own company's job");
        }

        jobApplicationRepository.findByUserIdAndJobId(jobApplicationRequestDTO.getUserId(), jobApplicationRequestDTO.getJobId())
                .ifPresent(application -> {
                    throw new IllegalArgumentException("You have already applied for this job");
                });

        JobApplication jobApplication = modelMapper.map(jobApplicationRequestDTO, JobApplication.class);
        jobApplication.setJob(job);
        jobApplication.setUser(user);
        jobApplication = jobApplicationRepository.save(jobApplication);
        return modelMapper.map(jobApplication, JobApplicationResponseDTO.class);
    }

    @Override
    public JobApplicationResponseDTO updateApplicationStatus(Long applicationId, JobApplicationRequestDTO jobApplicationRequestDTO) {
        JobApplication jobApplication = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found"));
        jobApplication.setStatus(jobApplicationRequestDTO.getStatus());
        jobApplication = jobApplicationRepository.save(jobApplication);
        return modelMapper.map(jobApplication, JobApplicationResponseDTO.class);
    }

    @Override
    public List<JobApplicationResponseDTO> getApplicationsByJob(Long jobId) {
        return jobApplicationRepository.findByJobId(jobId).stream()
                .map(application -> modelMapper.map(application, JobApplicationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<JobApplicationResponseDTO> getApplicationsByUser(Long userId) {
        return jobApplicationRepository.findByUserId(userId).stream()
                .map(application -> modelMapper.map(application, JobApplicationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void withdrawApplication(Long applicationId) {
        jobApplicationRepository.deleteById(applicationId);
    }
}
