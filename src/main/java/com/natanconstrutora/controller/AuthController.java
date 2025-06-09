package com.natanconstrutora.controller;

import com.natanconstrutora.dto.RefreshTokenRequest;
import com.natanconstrutora.dto.RefreshTokenResponse;
import com.natanconstrutora.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "APIs para autenticação e gerenciamento de tokens")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Método login comentado para evitar conflito com AutenticacaoController
    /*
    @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna um token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Parameter(description = "Credenciais de login", required = true)
            @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
    */

    @Operation(summary = "Renovar token", description = "Renova um token JWT usando o refresh token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token renovado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Refresh token inválido ou expirado")
    })
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            @Parameter(description = "Refresh token", required = true)
            @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest.getRefreshToken()));
    }

    // Método logout comentado para evitar conflito de mapeamento
    // @Operation(summary = "Realizar logout", description = "Invalida o token JWT atual")
    // @ApiResponses(value = {
    //     @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso")
    // })
    // @PostMapping("/logout")
    // public ResponseEntity<Void> logout() {
    //     authService.logout();
    //     return ResponseEntity.ok().build();
    // }
}