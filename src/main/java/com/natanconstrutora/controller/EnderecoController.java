package com.natanconstrutora.controller;

import com.natanconstrutora.model.Endereco;
import com.natanconstrutora.service.EnderecoService;
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

@RestController
@RequestMapping("/api/enderecos")
@Tag(name = "Endereços", description = "APIs para gerenciamento de endereços")
@SecurityRequirement(name = "bearer-jwt")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Operation(summary = "Listar endereços", description = "Retorna todos os endereços cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereços retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(enderecoService.listar());
    }

    @Operation(summary = "Buscar endereço", description = "Retorna um endereço específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.buscar(id));
    }

    @Operation(summary = "Criar endereço", description = "Cria um novo endereço")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<?> criar(
            @Parameter(description = "Dados do endereço", required = true)
            @RequestBody Endereco endereco) {
        return ResponseEntity.status(201).body(enderecoService.criar(endereco));
    }

    @Operation(summary = "Atualizar endereço", description = "Atualiza um endereço existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados do endereço", required = true)
            @RequestBody Endereco endereco) {
        return ResponseEntity.ok(enderecoService.atualizar(id, endereco));
    }

    @Operation(summary = "Excluir endereço", description = "Remove um endereço existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Endereço removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable Long id) {
        enderecoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar endereços por CEP", description = "Retorna endereços de um CEP específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereços retornados com sucesso"),
        @ApiResponse(responseCode = "400", description = "CEP inválido")
    })
    @GetMapping("/cep/{cep}")
    public ResponseEntity<?> buscarPorCep(
            @Parameter(description = "CEP do endereço", required = true)
            @PathVariable String cep) {
        return ResponseEntity.ok(enderecoService.buscarPorCep(cep));
    }

    @Operation(summary = "Buscar endereços por cidade", description = "Retorna endereços de uma cidade específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereços retornados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Cidade inválida")
    })
    @GetMapping("/cidade/{cidade}")
    public ResponseEntity<?> buscarPorCidade(
            @Parameter(description = "Cidade do endereço", required = true)
            @PathVariable String cidade) {
        return ResponseEntity.ok(enderecoService.buscarPorCidade(cidade));
    }

    @Operation(summary = "Buscar endereços por estado", description = "Retorna endereços de um estado específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereços retornados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Estado inválido")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> buscarPorEstado(
            @Parameter(description = "Estado do endereço", required = true)
            @PathVariable String estado) {
        return ResponseEntity.ok(enderecoService.buscarPorEstado(estado));
    }
} 