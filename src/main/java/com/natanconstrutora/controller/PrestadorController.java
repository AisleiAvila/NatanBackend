package com.natanconstrutora.controller;

import com.natanconstrutora.model.Prestador;
import com.natanconstrutora.service.PrestadorService;
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
@RequestMapping("/api/prestadores")
@Tag(name = "Prestadores", description = "APIs para gerenciamento de prestadores de serviços")
@SecurityRequirement(name = "bearer-jwt")
public class PrestadorController {

    @Autowired
    private PrestadorService prestadorService;

    @Operation(summary = "Listar todos os prestadores", description = "Retorna todos os prestadores cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de prestadores retornada com sucesso")
    })
    @GetMapping
    public List<Prestador> listarTodos() {
        return prestadorService.listarTodos();
    }

    @Operation(summary = "Buscar prestador por ID", description = "Retorna um prestador específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prestador encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Prestador não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Prestador> buscarPorId(
            @Parameter(description = "ID do prestador", required = true)
            @PathVariable Long id) {
        return prestadorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar prestador por email", description = "Retorna um prestador específico pelo seu email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prestador encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Prestador não encontrado")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<Prestador> buscarPorEmail(
            @Parameter(description = "Email do prestador", required = true)
            @PathVariable String email) {
        return prestadorService.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar prestadores por região", description = "Retorna todos os prestadores de uma região específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de prestadores retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Região não encontrada")
    })
    @GetMapping("/regiao/{regiaoId}")
    public ResponseEntity<List<Prestador>> buscarPorRegiao(
            @Parameter(description = "ID da região", required = true)
            @PathVariable Long regiaoId) {
        return ResponseEntity.ok(prestadorService.buscarPorRegiao(regiaoId));
    }

    @Operation(summary = "Buscar prestadores por categoria", description = "Retorna todos os prestadores de uma categoria específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de prestadores retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Prestador>> buscarPorCategoria(
            @Parameter(description = "ID da categoria", required = true)
            @PathVariable Long categoriaId) {
        return ResponseEntity.ok(prestadorService.buscarPorCategoria(categoriaId));
    }

    @Operation(summary = "Criar prestador", description = "Cria um novo prestador no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Prestador criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<Prestador> criar(
            @Parameter(description = "Dados do prestador", required = true)
            @RequestBody Prestador prestador) {
        return ResponseEntity.ok(prestadorService.criar(prestador));
    }

    @Operation(summary = "Atualizar prestador", description = "Atualiza os dados de um prestador existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prestador atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Prestador não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Prestador> atualizar(
            @Parameter(description = "ID do prestador", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do prestador", required = true)
            @RequestBody Prestador prestador) {
        try {
            return ResponseEntity.ok(prestadorService.atualizar(id, prestador));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Excluir prestador", description = "Remove um prestador do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Prestador removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Prestador não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do prestador", required = true)
            @PathVariable Long id) {
        try {
            prestadorService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Ativar prestador", description = "Ativa um prestador no sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prestador ativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Prestador não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PutMapping("/{id}/ativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Prestador> ativar(
            @Parameter(description = "ID do prestador", required = true)
            @PathVariable Long id) {
        try {
            prestadorService.ativar(id);
            return prestadorService.buscarPorId(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Desativar prestador", description = "Desativa um prestador no sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prestador desativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Prestador não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PutMapping("/{id}/desativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Prestador> desativar(
            @Parameter(description = "ID do prestador", required = true)
            @PathVariable Long id) {
        try {
            prestadorService.desativar(id);
            return prestadorService.buscarPorId(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 