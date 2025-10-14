package com.MyProject.Application.controllers;

import com.MyProject.Application.dto.LoginRequest;
import com.MyProject.Application.dto.LoginResponse;
import com.MyProject.Application.dto.RefreshTokenRequest;
import com.MyProject.Application.services.AuthService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        UserRepresentation user = authService.findUserByUsername(loginRequest.getUsername());
        if (user == null) {
            return ResponseEntity.status(404).body(
                    LoginResponse.builder()
                            .username(null)
                            .firstName(null)
                            .lastName(null)
                            .email(null)
                            .accessToken(null)
                            .refreshToken(null)
                            .build()
            );
        }

        LoginResponse response = authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        LoginResponse response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
