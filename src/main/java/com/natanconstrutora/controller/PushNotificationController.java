package com.natanconstrutora.controller;

import com.natanconstrutora.dto.PushNotificationRequest;
import com.natanconstrutora.service.PushNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificacoes-push")
@Tag(name = "Notificações Push", description = "APIs para envio de notificações push")
@SecurityRequirement(name = "bearer-jwt")
public class PushNotificationController {

    @Autowired
    private PushNotificationService pushNotificationService;

    @Operation(summary = "Enviar notificação", description = "Envia uma notificação push para um dispositivo específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificação enviada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/enviar")
    public ResponseEntity<Void> enviar(
            @Parameter(description = "Dados da notificação", required = true)
            @RequestBody PushNotificationRequest pushNotificationRequest) {
        pushNotificationService.enviar(pushNotificationRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enviar notificação para todos", description = "Envia uma notificação push para todos os dispositivos registrados (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificação enviada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PostMapping("/enviar-todos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> enviarParaTodos(
            @Parameter(description = "Dados da notificação", required = true)
            @RequestBody PushNotificationRequest pushNotificationRequest) {
        pushNotificationService.enviarParaTodos(pushNotificationRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Registrar dispositivo", description = "Registra um dispositivo para receber notificações push")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dispositivo registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/registrar")
    public ResponseEntity<Void> registrarDispositivo(
            @Parameter(description = "Token do dispositivo", required = true)
            @RequestParam String token) {
        pushNotificationService.registrarDispositivo(token);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remover dispositivo", description = "Remove um dispositivo do registro de notificações push")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dispositivo removido com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @DeleteMapping("/remover")
    public ResponseEntity<Void> removerDispositivo(
            @Parameter(description = "Token do dispositivo", required = true)
            @RequestParam String token) {
        pushNotificationService.removerDispositivo(token);
        return ResponseEntity.ok().build();
    }
} 