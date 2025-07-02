package com.jobportal.malawi.validation;

import com.jobportal.malawi.models.JobType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = JobTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidJobType {
    String message() default "Invalid job type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
