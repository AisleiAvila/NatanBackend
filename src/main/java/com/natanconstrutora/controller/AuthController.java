
package com.natanconstrutora.controller;

import com.natanconstrutora.model.Role;
import com.natanconstrutora.model.RoleName;
import com.natanconstrutora.model.User;
import com.natanconstrutora.repository.RoleRepository;
import com.natanconstrutora.repository.UserRepository;
import com.natanconstrutora.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", jwt);
        response.put("tokenType", "Bearer");
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Error: Username is already taken!"));
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                           signUpRequest.getEmail(),
                           passwordEncoder.encode(signUpRequest.getPassword()),
                           signUpRequest.getNome());

        Role userRole = roleRepository.findByName(RoleName.ROLE_CLIENTE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
    }

    // Inner classes for request bodies
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class SignUpRequest {
        private String nome;
        private String username;
        private String email;
        private String password;

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
