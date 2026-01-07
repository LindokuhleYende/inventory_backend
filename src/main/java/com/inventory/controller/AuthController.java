package com.inventory.controller;

import com.inventory.model.User;
import com.inventory.repository.UserRepository;
import com.inventory.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication APIs")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<?> register(@RequestBody User request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Username already exists"));
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }


    @PostMapping("/login")
    @Operation(summary = "Login a registered user")
    public ResponseEntity<?> login(@RequestBody User request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid username or password"));
        }

        String token = jwtTokenProvider.generateToken(user);

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "username", user.getUsername()
                )
        );
    }
}
