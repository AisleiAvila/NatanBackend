package com.natanconstrutora.service;

import com.natanconstrutora.model.Avaliacao;
import com.natanconstrutora.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public List<Avaliacao> listarTodas() {
        return avaliacaoRepository.findAll();
    }

    public Optional<Avaliacao> buscarPorId(Long id) {
        return avaliacaoRepository.findById(id);
    }

    public List<Avaliacao> buscarPorServico(Long servicoId) {
        return avaliacaoRepository.findByServicoId(servicoId);
    }

    public List<Avaliacao> buscarPorCliente(Long clienteId) {
        return avaliacaoRepository.findByClienteId(clienteId);
    }

    public List<Avaliacao> buscarPorPrestador(Long prestadorId) {
        return avaliacaoRepository.findByPrestadorId(prestadorId);
    }

    @Transactional
    public Avaliacao criar(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    @Transactional
    public Optional<Avaliacao> atualizar(Long id, Avaliacao avaliacao) {
        return avaliacaoRepository.findById(id)
            .map(existingAvaliacao -> {
                avaliacao.setId(id);
                return avaliacaoRepository.save(avaliacao);
            });
    }

    @Transactional
    public void excluir(Long id) {
        if (!avaliacaoRepository.existsById(id)) {
            throw new RuntimeException("Avaliação não encontrada");
        }
        avaliacaoRepository.deleteById(id);
    }
} 