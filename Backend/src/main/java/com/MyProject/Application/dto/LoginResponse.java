package com.MyProject.Application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LoginResponse {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String accessToken;
    private String refreshToken;

}
