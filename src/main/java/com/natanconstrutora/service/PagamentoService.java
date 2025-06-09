package com.natanconstrutora.service;

import com.natanconstrutora.model.Pagamento;
import com.natanconstrutora.model.Servico;
import com.natanconstrutora.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public List<Pagamento> listar() {
        return pagamentoRepository.findAll();
    }

    public Optional<Pagamento> buscar(Long id) {
        return pagamentoRepository.findById(id);
    }

    public List<Pagamento> buscarPorCliente(Long clienteId) {
        return pagamentoRepository.findByServico_ClienteId(clienteId);
    }

    public List<Pagamento> buscarPorPrestador(Long prestadorId) {
        return pagamentoRepository.findByServico_PrestadorId(prestadorId);
    }

    public List<Pagamento> buscarPorStatus(String status) {
        return pagamentoRepository.findByStatus(Pagamento.StatusPagamento.valueOf(status));
    }

    public List<Pagamento> buscarPorServico(Servico servico) {
        return pagamentoRepository.findByServico(servico);
    }

    public List<Pagamento> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return pagamentoRepository.findByDataPagamentoBetween(inicio, fim);
    }

    @Transactional
    public Pagamento criar(Pagamento pagamento) {
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setStatus(Pagamento.StatusPagamento.PENDENTE);
        return pagamentoRepository.save(pagamento);
    }

    @Transactional
    public Pagamento atualizar(Long id, Pagamento pagamento) {
        if (!pagamentoRepository.existsById(id)) {
            throw new RuntimeException("Pagamento não encontrado");
        }
        pagamento.setId(id);
        return pagamentoRepository.save(pagamento);
    }

    @Transactional
    public Pagamento aprovar(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        pagamento.setStatus(Pagamento.StatusPagamento.APROVADO);
        return pagamentoRepository.save(pagamento);
    }

    @Transactional
    public Pagamento recusar(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        pagamento.setStatus(Pagamento.StatusPagamento.RECUSADO);
        return pagamentoRepository.save(pagamento);
    }

    @Transactional
    public Pagamento cancelar(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        pagamento.setStatus(Pagamento.StatusPagamento.CANCELADO);
        return pagamentoRepository.save(pagamento);
    }

    @Transactional
    public void excluir(Long id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new RuntimeException("Pagamento não encontrado");
        }
        pagamentoRepository.deleteById(id);
    }

    @Transactional
    public Pagamento atualizarStatus(Long id, String status) {
        Pagamento pagamento = pagamentoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        pagamento.setStatus(Pagamento.StatusPagamento.valueOf(status));
        return pagamentoRepository.save(pagamento);
    }
} 