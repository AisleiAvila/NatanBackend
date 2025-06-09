package com.natanconstrutora.controller;

import com.natanconstrutora.model.Perfil;
import com.natanconstrutora.service.PerfilService;
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
@RequestMapping("/api/perfis")
@Tag(name = "Perfis", description = "APIs para gerenciamento de perfis de usuários")
@SecurityRequirement(name = "bearer-jwt")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @Operation(summary = "Listar todos os perfis", description = "Retorna todos os perfis cadastrados (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de perfis retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Perfil> listarTodos() {
        return perfilService.listarTodos();
    }

    @Operation(summary = "Buscar perfil por ID", description = "Retorna um perfil específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Perfil> buscarPorId(
            @Parameter(description = "ID do perfil", required = true)
            @PathVariable Long id) {
        return perfilService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar perfil por nome", description = "Retorna um perfil específico pelo seu nome")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Perfil> buscarPorNome(
            @Parameter(description = "Nome do perfil", required = true)
            @PathVariable String nome) {
        return perfilService.buscarPorNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar perfil", description = "Cria um novo perfil no sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Perfil criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Perfil> criar(
            @Parameter(description = "Dados do perfil", required = true)
            @RequestBody Perfil perfil) {
        return ResponseEntity.ok(perfilService.criar(perfil));
    }

    @Operation(summary = "Atualizar perfil", description = "Atualiza os dados de um perfil existente (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Perfil não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Perfil> atualizar(
            @Parameter(description = "ID do perfil", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do perfil", required = true)
            @RequestBody Perfil perfil) {
        try {
            return ResponseEntity.ok(perfilService.atualizar(id, perfil));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Excluir perfil", description = "Remove um perfil do sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Perfil removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Perfil não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do perfil", required = true)
            @PathVariable Long id) {
        try {
            perfilService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 