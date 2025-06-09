package com.natanconstrutora.service;

import com.natanconstrutora.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private PrestadorRepository prestadorRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> obterMetricas() {
        Map<String, Object> metricas = new HashMap<>();

        // Total de clientes
        metricas.put("totalClientes", clienteRepository.count());

        // Total de serviços
        metricas.put("totalServicos", servicoRepository.count());

        // Total de prestadores
        metricas.put("totalPrestadores", prestadorRepository.count());

        // Total de pagamentos
        metricas.put("totalPagamentos", pagamentoRepository.count());

        // Total de avaliações
        metricas.put("totalAvaliacoes", avaliacaoRepository.count());

        // Média de avaliações
        metricas.put("mediaAvaliacoes", avaliacaoRepository.findMediaAvaliacoes());

        // Faturamento total
        metricas.put("faturamentoTotal", pagamentoRepository.findFaturamentoTotal());

        // Faturamento do mês
        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fimMes = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().toLocalDate().lengthOfMonth())
            .withHour(23).withMinute(59).withSecond(59);
        metricas.put("faturamentoMes", pagamentoRepository.findFaturamentoPorPeriodo(inicioMes, fimMes));

        return metricas;
    }

    public Map<String, Object> obterMetricasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        Map<String, Object> metricas = new HashMap<>();

        // Total de serviços no período
        metricas.put("totalServicos", servicoRepository.countByDataCriacaoBetween(inicio, fim));

        // Total de pagamentos no período
        metricas.put("totalPagamentos", pagamentoRepository.countByDataPagamentoBetween(inicio, fim));

        // Total de avaliações no período
        metricas.put("totalAvaliacoes", avaliacaoRepository.countByDataCriacaoBetween(inicio, fim));

        // Média de avaliações no período
        metricas.put("mediaAvaliacoes", avaliacaoRepository.findMediaAvaliacoesPorPeriodo(inicio, fim));

        // Faturamento no período
        metricas.put("faturamento", pagamentoRepository.findFaturamentoPorPeriodo(inicio, fim));

        return metricas;
    }

    public Map<String, Object> obterResumoGeral() {
        Map<String, Object> resumo = new HashMap<>();
        resumo.put("totalSolicitacoes", solicitacaoRepository.count());
        resumo.put("totalUsuarios", userRepository.count());
        resumo.put("totalServicos", servicoRepository.count());
        resumo.put("totalAvaliacoes", avaliacaoRepository.count());
        return resumo;
    }

    public Map<String, Object> obterDadosServicos(LocalDate dataInicio, LocalDate dataFim) {
        Map<String, Object> dados = new HashMap<>();
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        
        dados.put("totalServicos", servicoRepository.countByDataCriacaoBetween(inicio, fim));
        dados.put("servicosPorPrestador", servicoRepository.findServicosPorPrestador(inicio, fim));
        dados.put("servicosPorCategoria", servicoRepository.findServicosPorCategoria(inicio, fim));
        
        return dados;
    }

    public Map<String, Object> obterDadosSolicitacoes(LocalDate dataInicio, LocalDate dataFim) {
        Map<String, Object> dados = new HashMap<>();
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        
        dados.put("totalSolicitacoes", solicitacaoRepository.countByCreatedAtBetween(inicio, fim));
        dados.put("solicitacoesPorStatus", solicitacaoRepository.findSolicitacoesPorStatus(inicio, fim));
        dados.put("solicitacoesPorRegiao", solicitacaoRepository.findSolicitacoesPorRegiao(inicio, fim));
        
        return dados;
    }

    public Map<String, Object> obterDadosPagamentos(LocalDate dataInicio, LocalDate dataFim) {
        Map<String, Object> dados = new HashMap<>();
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        
        dados.put("totalPagamentos", pagamentoRepository.countByDataPagamentoBetween(inicio, fim));
        dados.put("faturamentoPorPrestador", pagamentoRepository.findFaturamentoPorPrestador(inicio, fim));
        dados.put("faturamentoPorCategoria", pagamentoRepository.findFaturamentoPorCategoria(inicio, fim));
        
        return dados;
    }

    public Map<String, Object> obterDadosPrestadores(LocalDate dataInicio, LocalDate dataFim) {
        Map<String, Object> dados = new HashMap<>();
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        
        dados.put("totalPrestadores", prestadorRepository.countByDataCadastroBetween(inicio, fim));
        dados.put("prestadoresPorRegiao", prestadorRepository.findPrestadoresPorRegiao(inicio, fim));
        dados.put("prestadoresPorCategoria", prestadorRepository.findPrestadoresPorCategoria(inicio, fim));
        
        return dados;
    }

    public Map<String, Object> obterDadosClientes(LocalDate dataInicio, LocalDate dataFim) {
        Map<String, Object> dados = new HashMap<>();
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        
        dados.put("totalClientes", clienteRepository.countByDataCadastroBetween(inicio, fim));
        dados.put("clientesPorRegiao", clienteRepository.findClientesPorRegiao(inicio, fim));
        dados.put("clientesAtivos", clienteRepository.countByAtivoTrue());
        
        return dados;
    }
} 