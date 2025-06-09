package com.natanconstrutora.service;

import com.natanconstrutora.dto.LoginRequest;
import com.natanconstrutora.dto.LoginResponse;
import com.natanconstrutora.dto.RefreshTokenResponse;
import com.natanconstrutora.model.Usuario;
import com.natanconstrutora.repository.UsuarioRepository;
import com.natanconstrutora.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateToken(authentication);

        return new LoginResponse(accessToken, refreshToken, "Bearer", 3600000L);
    }

    public RefreshTokenResponse refreshToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Token de atualização inválido");
        }

        String email = tokenProvider.getEmailFromToken(refreshToken);
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            usuario, null, usuario.getAuthorities()
        );

        String newAccessToken = tokenProvider.generateToken(authentication);
        String newRefreshToken = tokenProvider.generateToken(authentication);

        RefreshTokenResponse response = new RefreshTokenResponse();
        response.setAccessToken(newAccessToken);
        response.setRefreshToken(newRefreshToken);
        response.setTokenType("Bearer");
        return response;
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
} 