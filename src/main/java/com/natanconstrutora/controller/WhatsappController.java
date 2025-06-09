package com.natanconstrutora.controller;

import com.natanconstrutora.dto.WhatsappRequest;
import com.natanconstrutora.service.WhatsappService;
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
@RequestMapping("/api/whatsapp")
@Tag(name = "WhatsApp", description = "APIs para envio de mensagens WhatsApp")
@SecurityRequirement(name = "bearer-jwt")
public class WhatsappController {

    @Autowired
    private WhatsappService whatsappService;

    @Operation(summary = "Enviar mensagem", description = "Envia uma mensagem WhatsApp para um número específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagem enviada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/enviar")
    public ResponseEntity<Void> enviar(
            @Parameter(description = "Dados da mensagem", required = true)
            @RequestBody WhatsappRequest whatsappRequest) {
        whatsappService.enviarMensagem(whatsappRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enviar mensagem de boas-vindas", description = "Envia uma mensagem de boas-vindas para um novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagem enviada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/boas-vindas")
    public ResponseEntity<Void> enviarBoasVindas(
            @Parameter(description = "Número do destinatário", required = true)
            @RequestParam String numero) {
        whatsappService.enviarMensagemBoasVindas(numero, "Cliente");
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enviar mensagem de recuperação de senha", description = "Envia uma mensagem com instruções para recuperação de senha")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagem enviada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/recuperacao-senha")
    public ResponseEntity<Void> enviarRecuperacaoSenha(
            @Parameter(description = "Número do destinatário", required = true)
            @RequestParam String numero) {
        whatsappService.enviarMensagemRecuperacaoSenha(numero, "123456");
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enviar mensagem de confirmação", description = "Envia uma mensagem de confirmação para um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagem enviada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/confirmacao")
    public ResponseEntity<Void> enviarConfirmacao(
            @Parameter(description = "Número do destinatário", required = true)
            @RequestParam String numero) {
        whatsappService.enviarMensagemConfirmacao(numero, "123456");
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enviar mensagem de notificação", description = "Envia uma mensagem de notificação para um usuário (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagem enviada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PostMapping("/notificacao")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> enviarNotificacao(
            @Parameter(description = "Dados da mensagem", required = true)
            @RequestBody WhatsappRequest whatsappRequest) {
        whatsappService.enviarMensagemNotificacao(whatsappRequest.getNumero(), whatsappRequest.getMensagem());
        return ResponseEntity.ok().build();
    }
} 