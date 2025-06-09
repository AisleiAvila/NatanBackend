package com.natanconstrutora.controller;

import com.natanconstrutora.model.Permissao;
import com.natanconstrutora.service.PermissaoService;
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
@RequestMapping("/api/permissoes")
@Tag(name = "Permissões", description = "APIs para gerenciamento de permissões do sistema")
@SecurityRequirement(name = "bearer-jwt")
public class PermissaoController {

    @Autowired
    private PermissaoService permissaoService;

    @Operation(summary = "Listar todas as permissões", description = "Retorna todas as permissões cadastradas (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de permissões retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Permissao> listarTodas() {
        return permissaoService.listarTodas();
    }

    @Operation(summary = "Buscar permissão por ID", description = "Retorna uma permissão específica pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permissão encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Permissão não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Permissao> buscarPorId(
            @Parameter(description = "ID da permissão", required = true)
            @PathVariable Long id) {
        return permissaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar permissão por nome", description = "Retorna uma permissão específica pelo seu nome")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permissão encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Permissão não encontrada")
    })
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Permissao> buscarPorNome(
            @Parameter(description = "Nome da permissão", required = true)
            @PathVariable String nome) {
        return permissaoService.buscarPorNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar permissão", description = "Cria uma nova permissão no sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Permissão criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Permissao> criar(
            @Parameter(description = "Dados da permissão", required = true)
            @RequestBody Permissao permissao) {
        return ResponseEntity.ok(permissaoService.criar(permissao));
    }

    @Operation(summary = "Atualizar permissão", description = "Atualiza os dados de uma permissão existente (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permissão atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Permissão não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Permissao> atualizar(
            @Parameter(description = "ID da permissão", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados da permissão", required = true)
            @RequestBody Permissao permissao) {
        try {
            return ResponseEntity.ok(permissaoService.atualizar(id, permissao));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Excluir permissão", description = "Remove uma permissão do sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Permissão removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Permissão não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da permissão", required = true)
            @PathVariable Long id) {
        try {
            permissaoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 