package com.fitness.gateway;

import com.fitness.gateway.user.RegisterRequest;
import com.fitness.gateway.user.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserSyncFilter implements WebFilter {

    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-Id");
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        RegisterRequest registerRequest = getUserDetails(token);

        if(userId == null) {
            userId = registerRequest.getKeycloakId();
        }

        if (userId != null && token != null) {
            log.info("Validating user with ID: {}", userId);
            String finalUserId = userId;
            String finalUserId1 = userId;
            return userService.validateUser(userId)
                    .flatMap(exist -> {
                        if (!exist) {
                            if(registerRequest != null) {
                                return userService.registerUser(registerRequest)
                                        .then(Mono.empty());
                            }
                            return Mono.empty();
                        } else {
                            log.warn("User {} is already exist", finalUserId1);
//                            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                            return Mono.empty();
                        }
                    })
                    .then(Mono.defer(() -> {
                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public HttpHeaders getHeaders() {
                                HttpHeaders headers = new HttpHeaders();
                                headers.addAll(exchange.getRequest().getHeaders());
                                headers.set("X-User-Id", finalUserId);
                                return headers;
                            }
                        };
                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    }));

        }
        return chain.filter(exchange);
    }

    private RegisterRequest getUserDetails(String token) {
        try {
            String tokenWithoutBearer = token.replace("Bearer ", "").trim();
            SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setEmail(claims.getStringClaim("email"));
            registerRequest.setKeycloakId(claims.getStringClaim("sub"));
            registerRequest.setPassword("dummy@123123");
            registerRequest.setFirstName(claims.getStringClaim("given_name"));
            registerRequest.setLastName(claims.getStringClaim("family_name"));
            return registerRequest;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
