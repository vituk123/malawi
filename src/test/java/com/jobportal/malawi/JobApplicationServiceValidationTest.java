package com.jobportal.malawi;

import com.jobportal.malawi.models.JobApplication;
import com.jobportal.malawi.models.Company;
import com.jobportal.malawi.models.Job;
import com.jobportal.malawi.models.JobStatus;
import com.jobportal.malawi.models.User;
import com.jobportal.malawi.models.UserProfile;
import com.jobportal.malawi.payload.request.dto.JobApplicationRequestDTO;
import com.jobportal.malawi.repository.JobApplicationRepository;
import com.jobportal.malawi.repository.UserRepository;
import com.jobportal.malawi.services.impl.JobApplicationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobApplicationServiceValidationTest {

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private com.jobportal.malawi.repository.UserProfileRepository userProfileRepository;

    @Mock
    private com.jobportal.malawi.repository.JobRepository jobRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private JobApplicationServiceImpl jobApplicationService;

    @Test
    public void testApplyForJob_alreadyApplied() {
        JobApplicationRequestDTO jobApplicationRequestDTO = new JobApplicationRequestDTO();
        jobApplicationRequestDTO.setUserId(1L);
        jobApplicationRequestDTO.setJobId(1L);

        // Create properly configured mock objects
        User user = new User();
        user.setId(1L);
        
        UserProfile userProfile = new UserProfile();
        userProfile.setSkills(List.of("Java", "Spring Boot")); // Ensure skills are not null/empty
        
        Job job = new Job();
        job.setId(1L);
        job.setStatus(JobStatus.ACTIVE);
        
        Company company = new Company();
        User companyUser = new User();
        companyUser.setId(2L); // Different from applicant user ID
        company.setUser(companyUser);
        job.setCompany(company);

        // Set up all necessary stubs for the method flow
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userProfileRepository.findByUser(user)).thenReturn(Optional.of(userProfile));
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(jobApplicationRepository.findByUserIdAndJobId(1L, 1L))
                .thenReturn(Optional.of(new JobApplication()));

        // Test should throw IllegalArgumentException for duplicate application
        assertThrows(IllegalArgumentException.class, () -> {
            jobApplicationService.applyForJob(jobApplicationRequestDTO);
        });
    }
}
