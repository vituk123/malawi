package com.jobportal.malawi.payload.request.dto;

import com.jobportal.malawi.models.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplicationRequestDTO {

    private String coverLetter;

    private String resumeUrl;

    private ApplicationStatus status;

    private Long userId;

    private Long jobId;
}
