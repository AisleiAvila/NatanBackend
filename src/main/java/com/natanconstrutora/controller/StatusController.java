package com.natanconstrutora.controller;

import com.natanconstrutora.model.Status;
import com.natanconstrutora.service.StatusService;
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
@RequestMapping("/api/status")
@Tag(name = "Status", description = "APIs para gerenciamento de status de solicitações")
@SecurityRequirement(name = "bearer-jwt")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @Operation(summary = "Listar todos os status", description = "Retorna todos os status cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de status retornada com sucesso")
    })
    @GetMapping
    public List<Status> listarTodos() {
        return statusService.listarTodos();
    }

    @Operation(summary = "Buscar status por ID", description = "Retorna um status específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Status não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Status> buscarPorId(
            @Parameter(description = "ID do status", required = true)
            @PathVariable Long id) {
        return statusService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar status por nome", description = "Retorna um status específico pelo seu nome")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Status não encontrado")
    })
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Status> buscarPorNome(
            @Parameter(description = "Nome do status", required = true)
            @PathVariable String nome) {
        return statusService.buscarPorNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar status", description = "Cria um novo status no sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Status criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Status> criar(
            @Parameter(description = "Dados do status", required = true)
            @RequestBody Status status) {
        return ResponseEntity.ok(statusService.criar(status));
    }

    @Operation(summary = "Atualizar status", description = "Atualiza os dados de um status existente (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Status não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Status> atualizar(
            @Parameter(description = "ID do status", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do status", required = true)
            @RequestBody Status status) {
        return statusService.atualizar(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Excluir status", description = "Remove um status do sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Status removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Status não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do status", required = true)
            @PathVariable Long id) {
        if (statusService.excluir(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 