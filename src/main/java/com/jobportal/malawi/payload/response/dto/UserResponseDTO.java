package com.jobportal.malawi.payload.response.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
}
