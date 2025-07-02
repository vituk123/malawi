package com.jobportal.malawi.payload.request.dto;

import com.jobportal.malawi.validation.ValidJobType;
import com.jobportal.malawi.validation.ValidMalawiLocation;
import com.jobportal.malawi.validation.ValidSalaryRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidSalaryRange
public class JobRequestDTO {

    @NotBlank
    @Size(min = 5, max = 100)
    private String title;

    @NotBlank
    @Size(min = 50)
    private String description;

    private String requirements;

    private double salary;

    @NotBlank
    @ValidMalawiLocation
    private String location;

    @ValidJobType
    private String jobType;

    private Long companyId;
}
