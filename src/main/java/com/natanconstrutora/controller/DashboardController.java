package com.natanconstrutora.controller;

import com.natanconstrutora.model.StatusSolicitacao;
import com.natanconstrutora.model.RegiaoEnum;
import com.natanconstrutora.repository.SolicitacaoRepository;
import com.natanconstrutora.repository.UserRepository;
import com.natanconstrutora.repository.ServicoRepository;
import com.natanconstrutora.repository.AvaliacaoRepository;
import com.natanconstrutora.repository.RegiaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import com.natanconstrutora.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin/dashboard")
@Tag(name = "Dashboard", description = "APIs para visualização de dados do dashboard")
@SecurityRequirement(name = "bearer-jwt")
public class DashboardController {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private RegiaoRepository regiaoRepository;

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/metricas")
    public Map<String, Object> getMetricas() {
        Map<String, Object> metricas = new HashMap<>();
        
        // Contadores gerais
        metricas.put("totalSolicitacoes", solicitacaoRepository.count());
        metricas.put("totalUsuarios", userRepository.count());
        metricas.put("totalServicos", servicoRepository.count());
        metricas.put("totalAvaliacoes", avaliacaoRepository.count());
        
        // Solicitações por status
        Map<String, Long> solicitacoesPorStatus = new HashMap<>();
        for (StatusSolicitacao status : StatusSolicitacao.values()) {
            solicitacoesPorStatus.put(status.name(), solicitacaoRepository.countByStatus(status));
        }
        metricas.put("solicitacoesPorStatus", solicitacoesPorStatus);
        
        // Solicitações por região
        Map<String, Long> solicitacoesPorRegiao = new HashMap<>();
        for (RegiaoEnum regiao : RegiaoEnum.values()) {
            solicitacoesPorRegiao.put(regiao.name(), 
                (long) solicitacaoRepository.findByRegiao(regiao).size());
        }
        metricas.put("solicitacoesPorRegiao", solicitacoesPorRegiao);
        
        return metricas;
    }

    @Operation(summary = "Obter resumo geral", description = "Retorna um resumo geral dos dados do sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resumo retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/resumo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> obterResumoGeral() {
        return ResponseEntity.ok(dashboardService.obterResumoGeral());
    }

    @Operation(summary = "Obter dados de serviços", description = "Retorna dados de serviços por período (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/servicos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> obterDadosServicos(
            @Parameter(description = "Data inicial do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data final do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return ResponseEntity.ok(dashboardService.obterDadosServicos(dataInicio, dataFim));
    }

    @Operation(summary = "Obter dados de solicitações", description = "Retorna dados de solicitações por período (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/solicitacoes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> obterDadosSolicitacoes(
            @Parameter(description = "Data inicial do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data final do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return ResponseEntity.ok(dashboardService.obterDadosSolicitacoes(dataInicio, dataFim));
    }

    @Operation(summary = "Obter dados de pagamentos", description = "Retorna dados de pagamentos por período (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/pagamentos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> obterDadosPagamentos(
            @Parameter(description = "Data inicial do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data final do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return ResponseEntity.ok(dashboardService.obterDadosPagamentos(dataInicio, dataFim));
    }

    @Operation(summary = "Obter dados de prestadores", description = "Retorna dados de prestadores por período (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/prestadores")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> obterDadosPrestadores(
            @Parameter(description = "Data inicial do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data final do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return ResponseEntity.ok(dashboardService.obterDadosPrestadores(dataInicio, dataFim));
    }

    @Operation(summary = "Obter dados de clientes", description = "Retorna dados de clientes por período (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/clientes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> obterDadosClientes(
            @Parameter(description = "Data inicial do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data final do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return ResponseEntity.ok(dashboardService.obterDadosClientes(dataInicio, dataFim));
    }
}
