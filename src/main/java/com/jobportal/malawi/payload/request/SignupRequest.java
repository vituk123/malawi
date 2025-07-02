package com.jobportal.malawi.payload.request;

import com.jobportal.malawi.validation.ValidPassword;
import com.jobportal.malawi.validation.ValidPhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @ValidPassword
    private String password;

    @ValidPhoneNumber
    private String phone;
}

