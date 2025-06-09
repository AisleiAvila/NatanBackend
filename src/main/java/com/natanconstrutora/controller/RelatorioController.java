package com.natanconstrutora.controller;

import com.natanconstrutora.service.RelatorioService;
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
@RequestMapping("/api/relatorios")
@Tag(name = "Relatórios", description = "APIs para geração de relatórios")
@SecurityRequirement(name = "bearer-jwt")
@PreAuthorize("hasRole('ADMIN')")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @Operation(summary = "Gerar relatório de serviços", description = "Gera um relatório detalhado dos serviços realizados em um período")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/servicos")
    public ResponseEntity<byte[]> gerarRelatorioServicos(
            @Parameter(description = "Data inicial do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data final do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return relatorioService.gerarRelatorioServicos(dataInicio, dataFim);
    }

    @Operation(summary = "Gerar relatório financeiro", description = "Gera um relatório financeiro detalhado em um período")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/financeiro")
    public ResponseEntity<byte[]> gerarRelatorioFinanceiro(
            @Parameter(description = "Data inicial do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data final do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return relatorioService.gerarRelatorioFinanceiro(dataInicio, dataFim);
    }

    @Operation(summary = "Gerar relatório de prestadores", description = "Gera um relatório de desempenho dos prestadores de serviço")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/prestadores")
    public ResponseEntity<byte[]> gerarRelatorioPrestadores(
            @Parameter(description = "Data inicial do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data final do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return relatorioService.gerarRelatorioPrestadores(dataInicio, dataFim);
    }

    @Operation(summary = "Gerar relatório de avaliações", description = "Gera um relatório das avaliações dos serviços realizados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/avaliacoes")
    public ResponseEntity<byte[]> gerarRelatorioAvaliacoes(
            @Parameter(description = "Data inicial do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data final do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return relatorioService.gerarRelatorioAvaliacoes(dataInicio, dataFim);
    }
} 