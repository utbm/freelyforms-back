package com.utbm.da50.freelyform.service;


import com.utbm.da50.freelyform.configuration.JwtTokenService;
import com.utbm.da50.freelyform.dto.auth.AuthenticationRequest;
import com.utbm.da50.freelyform.dto.auth.AuthenticationResponse;
import com.utbm.da50.freelyform.dto.user.RegisterUserRequest;
import com.utbm.da50.freelyform.enums.UserRole;
import com.utbm.da50.freelyform.model.User;
import com.utbm.da50.freelyform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<AuthenticationResponse> register(RegisterUserRequest request) {
        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(UserRole.USER);

        User user = userRepository.save(newUser);
        userRepository.save(user);

        var jwtToken = jwtTokenService.generateToken(user);
        
        return new ResponseEntity<>(AuthenticationResponse.builder().token(jwtToken).build(), HttpStatus.OK);
    }

    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtTokenService.generateToken(user);
        return new ResponseEntity<>(AuthenticationResponse.builder().token(jwtToken).build(), HttpStatus.OK);
    }

}
