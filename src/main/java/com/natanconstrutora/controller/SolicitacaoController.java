package com.natanconstrutora.controller;

import com.natanconstrutora.model.*;
import com.natanconstrutora.service.SolicitacaoService;
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

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/solicitacoes")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Solicitações", description = "APIs para gerenciamento de solicitações de serviços")
@SecurityRequirement(name = "bearer-jwt")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @Operation(summary = "Listar todas as solicitações", description = "Retorna todas as solicitações de serviços")
    @ApiResponse(responseCode = "200", description = "Lista de solicitações retornada com sucesso")
    @GetMapping
    public List<Solicitacao> listarTodas() {
        return solicitacaoService.buscarTodas();
    }

    @Operation(summary = "Buscar solicitação por ID", description = "Retorna uma solicitação específica pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitação encontrada"),
        @ApiResponse(responseCode = "404", description = "Solicitação não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> buscarPorId(
            @Parameter(description = "ID da solicitação", required = true)
            @PathVariable Long id) {
        Optional<Solicitacao> solicitacao = solicitacaoService.buscarPorId(id);
        return solicitacao.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar solicitações por status", description = "Retorna todas as solicitações com um status específico")
    @ApiResponse(responseCode = "200", description = "Lista de solicitações retornada com sucesso")
    @GetMapping("/status/{status}")
    public List<Solicitacao> buscarPorStatus(
            @Parameter(description = "Status das solicitações", required = true)
            @PathVariable StatusSolicitacao status) {
        return solicitacaoService.buscarPorStatus(status);
    }

    @Operation(summary = "Buscar solicitações por região", description = "Retorna todas as solicitações de uma região específica")
    @ApiResponse(responseCode = "200", description = "Lista de solicitações retornada com sucesso")
    @GetMapping("/regiao/{regiao}")
    public List<Solicitacao> buscarPorRegiao(
            @Parameter(description = "Região das solicitações", required = true)
            @PathVariable RegiaoEnum regiao) {
        return solicitacaoService.buscarPorRegiao(regiao);
    }

    @Operation(summary = "Buscar solicitações por cliente", description = "Retorna todas as solicitações de um cliente específico")
    @ApiResponse(responseCode = "200", description = "Lista de solicitações retornada com sucesso")
    @GetMapping("/cliente/{clienteId}")
    public List<Solicitacao> buscarPorCliente(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Long clienteId) {
        return solicitacaoService.buscarPorCliente(clienteId);
    }

    @Operation(summary = "Buscar solicitações por prestador", description = "Retorna todas as solicitações de um prestador específico")
    @ApiResponse(responseCode = "200", description = "Lista de solicitações retornada com sucesso")
    @GetMapping("/prestador/{prestadorId}")
    public List<Solicitacao> buscarPorPrestador(
            @Parameter(description = "ID do prestador", required = true)
            @PathVariable Long prestadorId) {
        return solicitacaoService.buscarPorPrestador(prestadorId);
    }

    @Operation(summary = "Atualizar status da solicitação", description = "Atualiza o status de uma solicitação específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Solicitação não encontrada")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<Solicitacao> atualizarStatus(
            @Parameter(description = "ID da solicitação", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novo status da solicitação", required = true)
            @RequestParam StatusSolicitacao novoStatus) {
        try {
            Solicitacao solicitacaoAtualizada = solicitacaoService.atualizarStatus(id, novoStatus);
            return ResponseEntity.ok(solicitacaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Atribuir prestador", description = "Atribui um prestador a uma solicitação específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prestador atribuído com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro ao atribuir prestador")
    })
    @PutMapping("/{id}/prestador")
    public ResponseEntity<Solicitacao> atribuirPrestador(
            @Parameter(description = "ID da solicitação", required = true)
            @PathVariable Long id,
            @Parameter(description = "ID do prestador", required = true)
            @RequestParam Long prestadorId) {
        try {
            Solicitacao solicitacaoAtualizada = solicitacaoService.atribuirPrestador(id, prestadorId);
            return ResponseEntity.ok(solicitacaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Sugerir prestadores", description = "Retorna uma lista de prestadores sugeridos para uma solicitação")
    @ApiResponse(responseCode = "200", description = "Lista de prestadores sugeridos retornada com sucesso")
    @GetMapping("/{id}/prestadores-sugeridos")
    public List<User> sugerirPrestadores(
            @Parameter(description = "ID da solicitação", required = true)
            @PathVariable Long id) {
        return solicitacaoService.sugerirPrestadores(id);
    }
}
