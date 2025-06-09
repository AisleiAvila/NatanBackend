package com.natanconstrutora.controller;

import com.natanconstrutora.model.Cliente;
import com.natanconstrutora.service.ClienteService;
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
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "APIs para gerenciamento de clientes")
@SecurityRequirement(name = "bearer-jwt")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar todos os clientes", description = "Retorna todos os clientes cadastrados (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }

    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar cliente por email", description = "Retorna um cliente específico pelo seu email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> buscarPorEmail(
            @Parameter(description = "Email do cliente", required = true)
            @PathVariable String email) {
        return clienteService.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar clientes por região", description = "Retorna todos os clientes de uma região específica (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Região não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/regiao/{regiaoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Cliente>> buscarPorRegiao(
            @Parameter(description = "ID da região", required = true)
            @PathVariable Long regiaoId) {
        return ResponseEntity.ok(clienteService.buscarPorRegiao(regiaoId));
    }

    @Operation(summary = "Criar cliente", description = "Cria um novo cliente no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<Cliente> criar(
            @Parameter(description = "Dados do cliente", required = true)
            @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.criar(cliente));
    }

    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do cliente", required = true)
            @RequestBody Cliente cliente) {
        return clienteService.atualizar(id, cliente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Excluir cliente", description = "Remove um cliente do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Long id) {
        try {
            clienteService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Ativar cliente", description = "Ativa um cliente no sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente ativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PutMapping("/{id}/ativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Cliente> ativar(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Long id) {
        try {
            clienteService.ativar(id);
            return ResponseEntity.ok(clienteService.buscarPorId(id).orElseThrow());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Desativar cliente", description = "Desativa um cliente no sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente desativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PutMapping("/{id}/desativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Cliente> desativar(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Long id) {
        try {
            clienteService.desativar(id);
            return ResponseEntity.ok(clienteService.buscarPorId(id).orElseThrow());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 