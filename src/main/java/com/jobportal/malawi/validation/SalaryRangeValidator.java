package com.jobportal.malawi.validation;

import com.jobportal.malawi.payload.request.dto.JobRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SalaryRangeValidator implements ConstraintValidator<ValidSalaryRange, JobRequestDTO> {

    @Override
    public boolean isValid(JobRequestDTO jobRequestDTO, ConstraintValidatorContext context) {
        if (jobRequestDTO.getSalary() <= 0) {
            return false;
        }
        // Add more specific salary range validation based on Malawian market
        return true;
    }
}
