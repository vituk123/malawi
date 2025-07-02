package com.jobportal.malawi.payload.request.dto;

import com.jobportal.malawi.validation.ValidIndustry;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequestDTO {

    @NotBlank
    private String name;

    private String description;

    @URL
    private String website;

    @NotBlank
    private String location;

    private String logoUrl;

    @ValidIndustry
    private String industry;

    private int size;

    private Long userId;
}
