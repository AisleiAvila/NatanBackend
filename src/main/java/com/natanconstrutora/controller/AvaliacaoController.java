package com.natanconstrutora.controller;

import com.natanconstrutora.model.Avaliacao;
import com.natanconstrutora.service.AvaliacaoService;
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
@RequestMapping("/api/avaliacoes")
@Tag(name = "Avaliações", description = "APIs para gerenciamento de avaliações de serviços")
@SecurityRequirement(name = "bearer-jwt")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Operation(summary = "Listar todas as avaliações", description = "Retorna todas as avaliações cadastradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de avaliações retornada com sucesso")
    })
    @GetMapping
    public List<Avaliacao> listarTodas() {
        return avaliacaoService.listarTodas();
    }

    @Operation(summary = "Buscar avaliação por ID", description = "Retorna uma avaliação específica pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliação encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> buscarPorId(
            @Parameter(description = "ID da avaliação", required = true)
            @PathVariable Long id) {
        return avaliacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar avaliações por solicitação", description = "Retorna todas as avaliações de uma solicitação específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de avaliações retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Solicitação não encontrada")
    })
    @GetMapping("/solicitacao/{solicitacaoId}")
    public ResponseEntity<List<Avaliacao>> buscarPorSolicitacao(
            @Parameter(description = "ID da solicitação", required = true)
            @PathVariable Long solicitacaoId) {
        return ResponseEntity.ok(avaliacaoService.buscarPorServico(solicitacaoId));
    }

    @Operation(summary = "Buscar avaliações por prestador", description = "Retorna todas as avaliações de um prestador específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de avaliações retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Prestador não encontrado")
    })
    @GetMapping("/prestador/{prestadorId}")
    public ResponseEntity<List<Avaliacao>> buscarPorPrestador(
            @Parameter(description = "ID do prestador", required = true)
            @PathVariable Long prestadorId) {
        return ResponseEntity.ok(avaliacaoService.buscarPorPrestador(prestadorId));
    }

    @Operation(summary = "Criar avaliação", description = "Cria uma nova avaliação no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Solicitação ou prestador não encontrado")
    })
    @PostMapping
    public ResponseEntity<Avaliacao> criar(
            @Parameter(description = "Dados da avaliação", required = true)
            @RequestBody Avaliacao avaliacao) {
        return ResponseEntity.ok(avaliacaoService.criar(avaliacao));
    }

    @Operation(summary = "Atualizar avaliação", description = "Atualiza os dados de uma avaliação existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> atualizar(
            @Parameter(description = "ID da avaliação", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados da avaliação", required = true)
            @RequestBody Avaliacao avaliacao) {
        return avaliacaoService.atualizar(id, avaliacao)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Excluir avaliação", description = "Remove uma avaliação do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Avaliação removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da avaliação", required = true)
            @PathVariable Long id) {
        try {
            avaliacaoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
