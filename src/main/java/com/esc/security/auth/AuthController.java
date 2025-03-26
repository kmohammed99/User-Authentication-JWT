package com.esc.security.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("esc/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping()
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.login(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authResponse.token())
                .body(authResponse);
    }

}
