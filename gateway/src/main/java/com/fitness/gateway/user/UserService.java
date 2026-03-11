package com.fitness.gateway.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final WebClient userServiceWebClient;

    public Mono<Boolean> validateUser(String userId) {
        log.info("Calling user validation API for UserId: {}", userId);
            return userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .onErrorResume(WebClientResponseException.class, e -> {
                        if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                            return Mono.error(new RuntimeException("user not found: "+ userId));
                        } else if(e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                            return Mono.error(new RuntimeException("Invalid request"));
                        }
                        return Mono.error(new RuntimeException("Unexpected error validating user: " + e.getMessage()));
                    });
    }

    public Mono<UserResponse> registerUser(RegisterRequest request) {
        log.info("Calling user registration API for email: {}", request.getEmail());
        return userServiceWebClient.post()
                .uri("/api/users/register")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if(e.getStatusCode() == HttpStatus.BAD_REQUEST)
                        return Mono.error(new RuntimeException("Bad Request: " + e.getMessage()));
                    else if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                        return Mono.error(new RuntimeException("Internal Server Error: " + e.getMessage()));
                    }
                    return Mono.error(new RuntimeException("Unexpected error registering user: " + e.getMessage()));
                });
    }
}
