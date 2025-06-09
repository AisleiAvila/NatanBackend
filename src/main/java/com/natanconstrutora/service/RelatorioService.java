package com.natanconstrutora.service;

import com.natanconstrutora.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class RelatorioService {

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

    public Map<String, Object> gerarRelatorioGeral() {
        Map<String, Object> relatorio = new HashMap<>();

        // Métricas gerais
        relatorio.put("totalClientes", clienteRepository.count());
        relatorio.put("totalServicos", servicoRepository.count());
        relatorio.put("totalPrestadores", prestadorRepository.count());
        relatorio.put("totalPagamentos", pagamentoRepository.count());
        relatorio.put("totalAvaliacoes", avaliacaoRepository.count());
        relatorio.put("mediaAvaliacoes", avaliacaoRepository.findMediaAvaliacoes());
        relatorio.put("faturamentoTotal", pagamentoRepository.findFaturamentoTotal());

        // Métricas por período
        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fimMes = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().toLocalDate().lengthOfMonth())
            .withHour(23).withMinute(59).withSecond(59);
        relatorio.put("faturamentoMes", pagamentoRepository.findFaturamentoPorPeriodo(inicioMes, fimMes));

        return relatorio;
    }

    public Map<String, Object> gerarRelatorioPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        Map<String, Object> relatorio = new HashMap<>();

        // Métricas do período
        relatorio.put("totalServicos", servicoRepository.countByDataCriacaoBetween(inicio, fim));
        relatorio.put("totalPagamentos", pagamentoRepository.countByDataPagamentoBetween(inicio, fim));
        relatorio.put("totalAvaliacoes", avaliacaoRepository.countByDataCriacaoBetween(inicio, fim));
        relatorio.put("mediaAvaliacoes", avaliacaoRepository.findMediaAvaliacoesPorPeriodo(inicio, fim));
        relatorio.put("faturamento", pagamentoRepository.findFaturamentoPorPeriodo(inicio, fim));

        // Métricas por prestador
        relatorio.put("servicosPorPrestador", servicoRepository.findServicosPorPrestador(inicio, fim));
        relatorio.put("avaliacoesPorPrestador", avaliacaoRepository.findAvaliacoesPorPrestador(inicio, fim));
        relatorio.put("faturamentoPorPrestador", pagamentoRepository.findFaturamentoPorPrestador(inicio, fim));

        // Métricas por categoria
        relatorio.put("servicosPorCategoria", servicoRepository.findServicosPorCategoria(inicio, fim));
        relatorio.put("avaliacoesPorCategoria", avaliacaoRepository.findAvaliacoesPorCategoria(inicio, fim));
        relatorio.put("faturamentoPorCategoria", pagamentoRepository.findFaturamentoPorCategoria(inicio, fim));

        return relatorio;
    }

    public Map<String, Object> gerarRelatorioPorPrestador(Long prestadorId, LocalDateTime inicio, LocalDateTime fim) {
        Map<String, Object> relatorio = new HashMap<>();

        // Métricas do prestador
        relatorio.put("totalServicos", servicoRepository.countByPrestadorIdAndDataCriacaoBetween(prestadorId, inicio, fim));
        relatorio.put("totalPagamentos", pagamentoRepository.countByPrestadorIdAndDataPagamentoBetween(prestadorId, inicio, fim));
        relatorio.put("totalAvaliacoes", avaliacaoRepository.countByPrestadorIdAndDataCriacaoBetween(prestadorId, inicio, fim));
        relatorio.put("mediaAvaliacoes", avaliacaoRepository.findMediaAvaliacoesPorPrestadorId(prestadorId, inicio, fim));
        relatorio.put("faturamento", pagamentoRepository.findFaturamentoPorPrestadorId(prestadorId, inicio, fim));

        return relatorio;
    }

    public ResponseEntity<byte[]> gerarRelatorioServicos(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        Map<String, Object> dados = new HashMap<>();
        dados.put("servicosPorPrestador", servicoRepository.findServicosPorPrestador(inicio, fim));
        dados.put("servicosPorCategoria", servicoRepository.findServicosPorCategoria(inicio, fim));
        return gerarPDF("relatorio-servicos", dados);
    }

    public ResponseEntity<byte[]> gerarRelatorioFinanceiro(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        Map<String, Object> dados = new HashMap<>();
        dados.put("faturamentoPorPrestador", pagamentoRepository.findFaturamentoPorPrestador(inicio, fim));
        dados.put("faturamentoPorCategoria", pagamentoRepository.findFaturamentoPorCategoria(inicio, fim));
        return gerarPDF("relatorio-financeiro", dados);
    }

    public ResponseEntity<byte[]> gerarRelatorioPrestadores(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        Map<String, Object> dados = new HashMap<>();
        dados.put("avaliacoesPorPrestador", avaliacaoRepository.findAvaliacoesPorPrestador(inicio, fim));
        dados.put("servicosPorPrestador", servicoRepository.findServicosPorPrestador(inicio, fim));
        return gerarPDF("relatorio-prestadores", dados);
    }

    public ResponseEntity<byte[]> gerarRelatorioAvaliacoes(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        Map<String, Object> dados = new HashMap<>();
        dados.put("avaliacoesPorCategoria", avaliacaoRepository.findAvaliacoesPorCategoria(inicio, fim));
        dados.put("mediaAvaliacoes", avaliacaoRepository.findMediaAvaliacoesPorPeriodo(inicio, fim));
        return gerarPDF("relatorio-avaliacoes", dados);
    }

    private ResponseEntity<byte[]> gerarPDF(String nomeRelatorio, Map<String, Object> dados) {
        // Implementar geração de PDF usando iText ou outra biblioteca
        return ResponseEntity.ok().build();
    }
} 