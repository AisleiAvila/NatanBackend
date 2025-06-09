package com.natanconstrutora.service;

import com.natanconstrutora.model.Servico;
import com.natanconstrutora.model.Regiao;
import com.natanconstrutora.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }

    public List<Servico> buscarAtivos() {
        return servicoRepository.findByAtivoTrue();
    }

    public List<Servico> buscarPorRegiao(Regiao regiao) {
        return servicoRepository.findByRegioesContaining(regiao);
    }

    public Optional<Servico> buscarPorId(Long id) {
        return servicoRepository.findById(id);
    }

    public List<Servico> buscarPorCliente(Long clienteId) {
        return servicoRepository.findByClienteId(clienteId);
    }

    public List<Servico> buscarPorPrestador(Long prestadorId) {
        return servicoRepository.findByPrestadorId(prestadorId);
    }

    public List<Servico> buscarPorCategoria(Long categoriaId) {
        return servicoRepository.findByCategoriaId(categoriaId);
    }

    public List<Servico> buscarPorStatus(String status) {
        return servicoRepository.findByStatus(status);
    }

    @Transactional
    public Servico criar(Servico servico) {
        return servicoRepository.save(servico);
    }

    @Transactional
    public Servico atualizar(Long id, Servico servico) {
        if (!servicoRepository.existsById(id)) {
            throw new RuntimeException("Serviço não encontrado");
        }
        servico.setId(id);
        return servicoRepository.save(servico);
    }

    @Transactional
    public void excluir(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new RuntimeException("Serviço não encontrado");
        }
        servicoRepository.deleteById(id);
    }

    @Transactional
    public Servico atualizarStatus(Long id, String status) {
        Servico servico = servicoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        servico.setStatus(status);
        return servicoRepository.save(servico);
    }

    public List<Servico> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return servicoRepository.findByDataCriacaoBetween(inicio, fim);
    }

    public List<Servico> buscarPorValor(Double valorMinimo, Double valorMaximo) {
        return servicoRepository.findByValorBetween(valorMinimo, valorMaximo);
    }

    public List<Servico> listar() {
        return servicoRepository.findAll();
    }

    public Optional<Servico> buscar(Long id) {
        return servicoRepository.findById(id);
    }
}
