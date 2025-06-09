package com.natanconstrutora.controller;

import com.natanconstrutora.dto.LoginRequest;
import com.natanconstrutora.dto.TokenResponse;
import com.natanconstrutora.service.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "APIs para autenticação e gerenciamento de tokens")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna um token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @Parameter(description = "Credenciais de login", required = true)
            @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(autenticacaoService.login(loginRequest.getEmail(), loginRequest.getSenha()));
    }

    @Operation(summary = "Validar token", description = "Valida um token JWT e retorna informações do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token válido"),
        @ApiResponse(responseCode = "401", description = "Token inválido ou expirado")
    })
    @GetMapping("/validar")
    public ResponseEntity<?> validarToken(
            @Parameter(description = "Token JWT", required = true)
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(autenticacaoService.validarToken(token));
    }

    @Operation(summary = "Renovar token", description = "Renova um token JWT expirado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token renovado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Token inválido ou não renovável")
    })
    @PostMapping("/renovar")
    public ResponseEntity<TokenResponse> renovarToken(
            @Parameter(description = "Token JWT expirado", required = true)
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(autenticacaoService.refreshToken(token));
    }

    @Operation(summary = "Realizar logout", description = "Invalida um token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Token inválido")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @Parameter(description = "Token JWT", required = true)
            @RequestHeader("Authorization") String token) {
        autenticacaoService.logout();
        return ResponseEntity.ok().build();
    }
} 