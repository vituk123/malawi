package com.jobportal.malawi.payload.response.dto;

import com.jobportal.malawi.models.JobStatus;
import com.jobportal.malawi.models.JobType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JobResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String requirements;
    private double salary;
    private String location;
    private JobType jobType;
    private JobStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CompanyResponseDTO company;
}
