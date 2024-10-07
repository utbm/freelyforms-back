package com.utbm.da50.freelyform.controller;

import com.utbm.da50.freelyform.dto.auth.AuthenticationRequest;
import com.utbm.da50.freelyform.dto.auth.AuthenticationResponse;
import com.utbm.da50.freelyform.dto.user.RegisterUserRequest;
import com.utbm.da50.freelyform.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterUserRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return authenticationService.login(request);
    }

}
