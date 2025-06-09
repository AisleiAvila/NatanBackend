package com.natanconstrutora.service;

import com.natanconstrutora.model.Permissao;
import com.natanconstrutora.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public List<Permissao> listarTodas() {
        return permissaoRepository.findAll();
    }

    public Optional<Permissao> buscarPorId(Long id) {
        return permissaoRepository.findById(id);
    }

    public Optional<Permissao> buscarPorNome(String nome) {
        return permissaoRepository.findByNome(nome);
    }

    @Transactional
    public Permissao criar(Permissao permissao) {
        if (permissaoRepository.existsByNome(permissao.getNome())) {
            throw new RuntimeException("Permissão já existe");
        }
        return permissaoRepository.save(permissao);
    }

    @Transactional
    public Permissao atualizar(Long id, Permissao permissao) {
        if (!permissaoRepository.existsById(id)) {
            throw new RuntimeException("Permissão não encontrada");
        }

        Optional<Permissao> permissaoExistente = permissaoRepository.findByNome(permissao.getNome());
        if (permissaoExistente.isPresent() && !permissaoExistente.get().getId().equals(id)) {
            throw new RuntimeException("Nome de permissão já existe");
        }

        permissao.setId(id);
        return permissaoRepository.save(permissao);
    }

    @Transactional
    public void excluir(Long id) {
        if (!permissaoRepository.existsById(id)) {
            throw new RuntimeException("Permissão não encontrada");
        }
        permissaoRepository.deleteById(id);
    }
} 