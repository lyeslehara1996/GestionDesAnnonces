package com.MyProject.Application.services;

import com.MyProject.Application.dto.LoginResponse;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Map;

public interface AuthService {

    UserRepresentation findUserByUsername(String username);

    LoginResponse loginUser(String username, String password);

    LoginResponse refreshToken(String refreshToken);
}
