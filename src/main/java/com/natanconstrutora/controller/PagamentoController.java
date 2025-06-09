package com.natanconstrutora.controller;

import com.natanconstrutora.model.Pagamento;
import com.natanconstrutora.service.PagamentoService;
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
@RequestMapping("/api/pagamentos")
@Tag(name = "Pagamentos", description = "APIs para gerenciamento de pagamentos")
@SecurityRequirement(name = "bearer-jwt")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @Operation(summary = "Listar pagamentos", description = "Retorna todos os pagamentos cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamentos retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(pagamentoService.listar());
    }

    @Operation(summary = "Buscar pagamento", description = "Retorna um pagamento específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamento encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID do pagamento", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(pagamentoService.buscar(id));
    }

    @Operation(summary = "Criar pagamento", description = "Cria um novo pagamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<?> criar(
            @Parameter(description = "Dados do pagamento", required = true)
            @RequestBody Pagamento pagamento) {
        return ResponseEntity.status(201).body(pagamentoService.criar(pagamento));
    }

    @Operation(summary = "Atualizar pagamento", description = "Atualiza um pagamento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamento atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do pagamento", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados do pagamento", required = true)
            @RequestBody Pagamento pagamento) {
        return ResponseEntity.ok(pagamentoService.atualizar(id, pagamento));
    }

    @Operation(summary = "Excluir pagamento", description = "Remove um pagamento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pagamento removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do pagamento", required = true)
            @PathVariable Long id) {
        pagamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar pagamentos por cliente", description = "Retorna pagamentos de um cliente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamentos retornados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> buscarPorCliente(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(pagamentoService.buscarPorCliente(id));
    }

    @Operation(summary = "Buscar pagamentos por prestador", description = "Retorna pagamentos de um prestador específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamentos retornados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Prestador não encontrado")
    })
    @GetMapping("/prestador/{id}")
    public ResponseEntity<?> buscarPorPrestador(
            @Parameter(description = "ID do prestador", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(pagamentoService.buscarPorPrestador(id));
    }

    @Operation(summary = "Buscar pagamentos por status", description = "Retorna pagamentos com um status específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamentos retornados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Status inválido")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<?> buscarPorStatus(
            @Parameter(description = "Status do pagamento", required = true)
            @PathVariable String status) {
        return ResponseEntity.ok(pagamentoService.buscarPorStatus(status));
    }

    @Operation(summary = "Atualizar status", description = "Atualiza o status de um pagamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Status inválido"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(
            @Parameter(description = "ID do pagamento", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novo status", required = true)
            @RequestParam String status) {
        return ResponseEntity.ok(pagamentoService.atualizarStatus(id, status));
    }
} 