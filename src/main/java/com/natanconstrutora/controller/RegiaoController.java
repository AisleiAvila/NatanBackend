package com.natanconstrutora.controller;

import com.natanconstrutora.model.RegiaoEnum;
import com.natanconstrutora.service.RegiaoService;
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
@RequestMapping("/api/regioes")
@Tag(name = "Regiões", description = "APIs para gerenciamento de regiões de atendimento")
@SecurityRequirement(name = "bearer-jwt")
public class RegiaoController {

    @Autowired
    private RegiaoService regiaoService;

    @Operation(summary = "Listar todas as regiões", description = "Retorna todas as regiões cadastradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de regiões retornada com sucesso")
    })
    @GetMapping
    public List<RegiaoEnum> listarTodas() {
        return regiaoService.listarTodas();
    }

    @Operation(summary = "Buscar região por nome", description = "Retorna uma região específica pelo seu nome")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Região encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Região não encontrada")
    })
    @GetMapping("/{nome}")
    public ResponseEntity<RegiaoEnum> buscarPorNome(
            @Parameter(description = "Nome da região", required = true)
            @PathVariable String nome) {
        RegiaoEnum regiao = regiaoService.buscarPorNome(nome);
        return regiao != null ? ResponseEntity.ok(regiao) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Criar região", description = "Cria uma nova região no sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Região criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegiaoEnum> criar(
            @Parameter(description = "Nome da região", required = true)
            @RequestBody String nome) {
        return ResponseEntity.ok(regiaoService.criar(nome));
    }

    @Operation(summary = "Atualizar região", description = "Atualiza os dados de uma região existente (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Região atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Região não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PutMapping("/{nomeAntigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegiaoEnum> atualizar(
            @Parameter(description = "Nome atual da região", required = true)
            @PathVariable String nomeAntigo,
            @Parameter(description = "Novo nome da região", required = true)
            @RequestBody String nomeNovo) {
        try {
            return ResponseEntity.ok(regiaoService.atualizar(nomeAntigo, nomeNovo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Excluir região", description = "Remove uma região do sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Região removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Região não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @DeleteMapping("/{nome}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "Nome da região", required = true)
            @PathVariable String nome) {
        try {
            regiaoService.excluir(nome);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 