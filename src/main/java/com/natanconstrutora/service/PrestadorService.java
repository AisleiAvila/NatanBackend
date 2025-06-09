package com.natanconstrutora.service;

import com.natanconstrutora.model.Prestador;
import com.natanconstrutora.model.RegiaoEnum;
import com.natanconstrutora.repository.PrestadorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestadorService {

    @Autowired
    private PrestadorRepository prestadorRepository;

    public List<Prestador> listarTodos() {
        return prestadorRepository.findAll();
    }

    public Optional<Prestador> buscarPorId(Long id) {
        return prestadorRepository.findById(id);
    }

    public Optional<Prestador> buscarPorEmail(String email) {
        return prestadorRepository.findByEmail(email);
    }

    public Optional<Prestador> buscarPorNif(String nif) {
        return prestadorRepository.findByNif(nif);
    }

    public List<Prestador> buscarPorNome(String nome) {
        return prestadorRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional
    public Prestador criar(Prestador prestador) {
        if (prestadorRepository.existsByEmail(prestador.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        if (prestadorRepository.existsByNif(prestador.getNif())) {
            throw new RuntimeException("NIF já cadastrado");
        }
        return prestadorRepository.save(prestador);
    }

    @Transactional
    public Prestador atualizar(Long id, Prestador prestador) {
        if (!prestadorRepository.existsById(id)) {
            throw new RuntimeException("Prestador não encontrado");
        }

        Optional<Prestador> prestadorExistente = prestadorRepository.findByEmail(prestador.getEmail());
        if (prestadorExistente.isPresent() && !prestadorExistente.get().getId().equals(id)) {
            throw new RuntimeException("Email já cadastrado");
        }

        prestadorExistente = prestadorRepository.findByNif(prestador.getNif());
        if (prestadorExistente.isPresent() && !prestadorExistente.get().getId().equals(id)) {
            throw new RuntimeException("NIF já cadastrado");
        }

        prestador.setId(id);
        return prestadorRepository.save(prestador);
    }

    @Transactional
    public void excluir(Long id) {
        if (!prestadorRepository.existsById(id)) {
            throw new RuntimeException("Prestador não encontrado");
        }
        prestadorRepository.deleteById(id);
    }

    @Transactional
    public void ativar(Long id) {
        Prestador prestador = prestadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
        prestador.setAtivo(true);
        prestadorRepository.save(prestador);
    }

    @Transactional
    public void desativar(Long id) {
        Prestador prestador = prestadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
        prestador.setAtivo(false);
        prestadorRepository.save(prestador);
    }

    public List<Prestador> buscarPorAvaliacaoMedia(Double avaliacaoMedia) {
        return prestadorRepository.findByAvaliacaoMediaGreaterThanEqual(avaliacaoMedia);
    }

    public List<Prestador> buscarPorRegiao(RegiaoEnum regiao) {
        return prestadorRepository.findByRegiao(regiao);
    }

    public List<Prestador> buscarPorCategoria(Long categoriaId) {
        return prestadorRepository.findByCategoriasId(categoriaId);
    }
}
