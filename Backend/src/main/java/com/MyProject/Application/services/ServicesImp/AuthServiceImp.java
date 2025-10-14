package com.MyProject.Application.services.ServicesImp;

import com.MyProject.Application.dto.LoginResponse;
import com.MyProject.Application.services.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    private final Keycloak keycloak;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public UserRepresentation findUserByUsername(String username) {

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> users = usersResource.search(username, 0, 1);
        return users.isEmpty() ? null : users.get(0);
    }

    public LoginResponse loginUser(String username, String password) {
        try{

            String tokenUrl = "http://127.0.0.1:8080/realms/SCPI_InvestPLUS/protocol/openid-connect/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("client_id", "SCPI-client");
            body.add("grant_type", "password");
            body.add("username", username);
            body.add("password", password);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);


            if (response.getStatusCode() == HttpStatus.OK) {
                String accessToken = (String) response.getBody().get("access_token");
                String refreshToken = (String) response.getBody().get("refresh_token");
                return new LoginResponse(username, null, null, null, accessToken, refreshToken);
            } else {
                throw new RuntimeException("Login failed");
            }

        } catch (HttpClientErrorException e) {
            System.out.println("❌ Keycloak error: " + e.getStatusCode());
            System.out.println(e.getResponseBodyAsString());
            throw e;
        }

    }


    @Override
    public LoginResponse refreshToken(String refreshToken) {
        String url = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", clientId);
        formData.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("Échec du rafraîchissement du token Keycloak");
            }

            Map<String, Object> tokens = response.getBody();

            return LoginResponse.builder()
                    .accessToken((String) tokens.get("access_token"))
                    .refreshToken((String) tokens.get("refresh_token"))
                    .build();

        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors du rafraîchissement du token : "
                    + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        }
    }
}