package com.jobportal.malawi.payload.response.dto;

import com.jobportal.malawi.models.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JobApplicationResponseDTO {

    private Long id;
    private String coverLetter;
    private String resumeUrl;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
    private JobResponseDTO job;
    private UserResponseDTO user;
}
