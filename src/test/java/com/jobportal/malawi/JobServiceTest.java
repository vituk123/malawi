package com.jobportal.malawi;

import com.jobportal.malawi.models.Company;
import com.jobportal.malawi.models.Job;
import com.jobportal.malawi.payload.request.dto.JobRequestDTO;
import com.jobportal.malawi.repository.CompanyRepository;
import com.jobportal.malawi.repository.JobRepository;
import com.jobportal.malawi.services.impl.JobServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private JobServiceImpl jobService;

    @Test
    public void testCreateJob() {
        JobRequestDTO jobRequestDTO = new JobRequestDTO();
        jobRequestDTO.setCompanyId(1L);

        Company company = new Company();
        company.setId(1L);

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(modelMapper.map(any(JobRequestDTO.class), eq(Job.class))).thenReturn(new Job());
        when(jobRepository.save(any(Job.class))).thenReturn(new Job());

        jobService.createJob(jobRequestDTO);
    }
}
