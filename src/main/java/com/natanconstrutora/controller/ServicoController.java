package com.natanconstrutora.controller;

import com.natanconstrutora.model.Servico;
import com.natanconstrutora.model.Regiao;
import com.natanconstrutora.service.ServicoService;
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
@RequestMapping("/api/servicos")
@Tag(name = "Serviços", description = "APIs para gerenciamento de serviços")
@SecurityRequirement(name = "bearer-jwt")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @Operation(summary = "Listar serviços", description = "Retorna todos os serviços cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serviços retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(servicoService.listar());
    }

    @Operation(summary = "Buscar serviço", description = "Retorna um serviço específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serviço encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID do serviço", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(servicoService.buscar(id));
    }

    @Operation(summary = "Criar serviço", description = "Cria um novo serviço (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> criar(
            @Parameter(description = "Dados do serviço", required = true)
            @RequestBody Servico servico) {
        return ResponseEntity.status(201).body(servicoService.criar(servico));
    }

    @Operation(summary = "Atualizar serviço", description = "Atualiza um serviço existente (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do serviço", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados do serviço", required = true)
            @RequestBody Servico servico) {
        return ResponseEntity.ok(servicoService.atualizar(id, servico));
    }

    @Operation(summary = "Excluir serviço", description = "Remove um serviço existente (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Serviço removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do serviço", required = true)
            @PathVariable Long id) {
        servicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar serviços por categoria", description = "Retorna serviços de uma categoria específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serviços retornados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/categoria/{id}")
    public ResponseEntity<?> buscarPorCategoria(
            @Parameter(description = "ID da categoria", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(servicoService.buscarPorCategoria(id));
    }

    @Operation(summary = "Buscar serviços por prestador", description = "Retorna serviços de um prestador específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serviços retornados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Prestador não encontrado")
    })
    @GetMapping("/prestador/{id}")
    public ResponseEntity<?> buscarPorPrestador(
            @Parameter(description = "ID do prestador", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(servicoService.buscarPorPrestador(id));
    }
}
