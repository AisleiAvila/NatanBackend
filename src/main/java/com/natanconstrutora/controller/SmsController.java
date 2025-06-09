package com.natanconstrutora.controller;

import com.natanconstrutora.dto.SmsRequest;
import com.natanconstrutora.service.SmsService;
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
@RequestMapping("/api/sms")
@Tag(name = "SMS", description = "APIs para envio de mensagens SMS")
@SecurityRequirement(name = "bearer-jwt")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Operation(summary = "Enviar SMS", description = "Envia uma mensagem SMS para um número específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "SMS enviado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/enviar")
    public ResponseEntity<Void> enviar(
            @Parameter(description = "Dados do SMS", required = true)
            @RequestBody SmsRequest smsRequest) {
        smsService.enviar(smsRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enviar SMS de boas-vindas", description = "Envia um SMS de boas-vindas para um novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "SMS enviado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/boas-vindas")
    public ResponseEntity<Void> enviarBoasVindas(
            @Parameter(description = "Número do destinatário", required = true)
            @RequestParam String numero) {
        smsService.enviarBoasVindas(numero);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enviar SMS de recuperação de senha", description = "Envia um SMS com instruções para recuperação de senha")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "SMS enviado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/recuperacao-senha")
    public ResponseEntity<Void> enviarRecuperacaoSenha(
            @Parameter(description = "Número do destinatário", required = true)
            @RequestParam String numero) {
        smsService.enviarRecuperacaoSenha(numero);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enviar SMS de confirmação", description = "Envia um SMS de confirmação para um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "SMS enviado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/confirmacao")
    public ResponseEntity<Void> enviarConfirmacao(
            @Parameter(description = "Número do destinatário", required = true)
            @RequestParam String numero) {
        smsService.enviarConfirmacao(numero);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enviar SMS de notificação", description = "Envia um SMS de notificação para um usuário (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "SMS enviado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PostMapping("/notificacao")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> enviarNotificacao(
            @Parameter(description = "Dados do SMS", required = true)
            @RequestBody SmsRequest smsRequest) {
        smsService.enviarNotificacao(smsRequest);
        return ResponseEntity.ok().build();
    }
} 