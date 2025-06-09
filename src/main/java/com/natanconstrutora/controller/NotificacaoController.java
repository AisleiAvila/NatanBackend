package com.natanconstrutora.controller;

import com.natanconstrutora.model.Notificacao;
import com.natanconstrutora.service.NotificacaoService;
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

import java.util.List;

@RestController
@RequestMapping("/api/notificacoes")
@Tag(name = "Notificações", description = "APIs para gerenciamento de notificações")
@SecurityRequirement(name = "bearer-jwt")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @Operation(summary = "Listar todas as notificações", description = "Retorna todas as notificações do usuário logado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de notificações retornada com sucesso")
    })
    @GetMapping
    public List<Notificacao> listarTodas() {
        return notificacaoService.listarTodas();
    }

    @Operation(summary = "Buscar notificação por ID", description = "Retorna uma notificação específica pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificação encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Notificação não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> buscarPorId(
            @Parameter(description = "ID da notificação", required = true)
            @PathVariable Long id) {
        return notificacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar notificações não lidas", description = "Retorna todas as notificações não lidas do usuário logado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de notificações retornada com sucesso")
    })
    @GetMapping("/nao-lidas")
    public List<Notificacao> buscarNaoLidas() {
        return notificacaoService.buscarNaoLidas();
    }

    @Operation(summary = "Marcar notificação como lida", description = "Marca uma notificação específica como lida")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificação marcada como lida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Notificação não encontrada")
    })
    @PutMapping("/{id}/ler")
    public ResponseEntity<Notificacao> marcarComoLida(
            @Parameter(description = "ID da notificação", required = true)
            @PathVariable Long id) {
        return notificacaoService.marcarComoLida(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Marcar todas como lidas", description = "Marca todas as notificações do usuário logado como lidas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificações marcadas como lidas com sucesso")
    })
    @PutMapping("/ler-todas")
    public ResponseEntity<Void> marcarTodasComoLidas() {
        notificacaoService.marcarTodasComoLidas();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Excluir notificação", description = "Remove uma notificação do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Notificação removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Notificação não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da notificação", required = true)
            @PathVariable Long id) {
        if (notificacaoService.excluir(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Excluir todas as notificações", description = "Remove todas as notificações do usuário logado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Notificações removidas com sucesso")
    })
    @DeleteMapping
    public ResponseEntity<Void> excluirTodas() {
        notificacaoService.excluirTodas();
        return ResponseEntity.noContent().build();
    }
} 