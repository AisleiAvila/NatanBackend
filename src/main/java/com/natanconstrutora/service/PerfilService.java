package com.natanconstrutora.service;

import com.natanconstrutora.model.Perfil;
import com.natanconstrutora.model.Permissao;
import com.natanconstrutora.repository.PerfilRepository;
import com.natanconstrutora.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    public List<Perfil> listarTodos() {
        return perfilRepository.findAll();
    }

    public Optional<Perfil> buscarPorId(Long id) {
        return perfilRepository.findById(id);
    }

    public Optional<Perfil> buscarPorNome(String nome) {
        return perfilRepository.findByNome(nome);
    }

    @Transactional
    public Perfil criar(Perfil perfil) {
        if (perfilRepository.existsByNome(perfil.getNome())) {
            throw new RuntimeException("Perfil já existe");
        }
        return perfilRepository.save(perfil);
    }

    @Transactional
    public Perfil atualizar(Long id, Perfil perfil) {
        if (!perfilRepository.existsById(id)) {
            throw new RuntimeException("Perfil não encontrado");
        }

        Optional<Perfil> perfilExistente = perfilRepository.findByNome(perfil.getNome());
        if (perfilExistente.isPresent() && !perfilExistente.get().getId().equals(id)) {
            throw new RuntimeException("Nome de perfil já existe");
        }

        perfil.setId(id);
        return perfilRepository.save(perfil);
    }

    @Transactional
    public void excluir(Long id) {
        if (!perfilRepository.existsById(id)) {
            throw new RuntimeException("Perfil não encontrado");
        }
        perfilRepository.deleteById(id);
    }

    @Transactional
    public void adicionarPermissao(Long perfilId, Long permissaoId) {
        Perfil perfil = perfilRepository.findById(perfilId)
            .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        Permissao permissao = permissaoRepository.findById(permissaoId)
            .orElseThrow(() -> new RuntimeException("Permissão não encontrada"));
        perfil.getPermissoes().add(permissao);
        perfilRepository.save(perfil);
    }

    @Transactional
    public void removerPermissao(Long perfilId, Long permissaoId) {
        Perfil perfil = perfilRepository.findById(perfilId)
            .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        Permissao permissao = permissaoRepository.findById(permissaoId)
            .orElseThrow(() -> new RuntimeException("Permissão não encontrada"));
        perfil.getPermissoes().remove(permissao);
        perfilRepository.save(perfil);
    }
} 