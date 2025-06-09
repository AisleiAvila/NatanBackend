package com.natanconstrutora.controller;

import com.natanconstrutora.service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/auditoria")
@Tag(name = "Auditoria", description = "APIs para consulta de registros de auditoria")
@SecurityRequirement(name = "bearer-jwt")
@PreAuthorize("hasRole('ADMIN')")
public class AuditoriaController {

    @Autowired
    private AuditoriaService auditoriaService;

    @Operation(summary = "Listar registros de auditoria", description = "Retorna todos os registros de auditoria do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros retornados com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping
    public ResponseEntity<?> listarRegistros() {
        return ResponseEntity.ok(auditoriaService.listarRegistros());
    }

    @Operation(summary = "Buscar registros por período", description = "Retorna registros de auditoria em um período específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros retornados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/periodo")
    public ResponseEntity<?> buscarPorPeriodo(
            @Parameter(description = "Data inicial do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data final do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return ResponseEntity.ok(auditoriaService.buscarPorPeriodo(dataInicio, dataFim));
    }

    @Operation(summary = "Buscar registros por usuário", description = "Retorna registros de auditoria de um usuário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros retornados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> buscarPorUsuario(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(auditoriaService.buscarPorUsuario(id));
    }

    @Operation(summary = "Buscar registros por tipo", description = "Retorna registros de auditoria de um tipo específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros retornados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Tipo inválido"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> buscarPorTipo(
            @Parameter(description = "Tipo de registro", required = true)
            @PathVariable String tipo) {
        return ResponseEntity.ok(auditoriaService.buscarPorTipo(tipo));
    }

    @Operation(summary = "Limpar registros antigos", description = "Remove registros de auditoria mais antigos que a data especificada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros removidos com sucesso"),
        @ApiResponse(responseCode = "400", description = "Data inválida"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @DeleteMapping("/limpar")
    public ResponseEntity<?> limparRegistrosAntigos(
            @Parameter(description = "Data limite para remoção", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataLimite) {
        auditoriaService.limparRegistrosAntigos(dataLimite);
        return ResponseEntity.ok().build();
    }
} 