package com.jobportal.malawi.payload.response.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CompanyResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String website;
    private String location;
    private String logoUrl;
    private String industry;
    private int size;
    private LocalDateTime createdAt;
    private List<JobResponseDTO> jobs;
}
