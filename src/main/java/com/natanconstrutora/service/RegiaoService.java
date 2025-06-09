package com.natanconstrutora.service;

import com.natanconstrutora.model.RegiaoEnum;
import com.natanconstrutora.repository.RegiaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class RegiaoService {

    @Autowired
    private RegiaoRepository regiaoRepository;

    public List<RegiaoEnum> listarTodas() {
        return Arrays.asList(RegiaoEnum.values());
    }

    public RegiaoEnum buscarPorNome(String nome) {
        try {
            return RegiaoEnum.valueOf(nome);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Região não encontrada");
        }
    }

    @Transactional
    public RegiaoEnum criar(String nome) {
        try {
            return RegiaoEnum.valueOf(nome);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Região inválida");
        }
    }

    @Transactional
    public RegiaoEnum atualizar(String nomeAntigo, String nomeNovo) {
        try {
            RegiaoEnum.valueOf(nomeAntigo);
            return RegiaoEnum.valueOf(nomeNovo);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Região inválida");
        }
    }

    @Transactional
    public void excluir(String nome) {
        try {
            RegiaoEnum.valueOf(nome);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Região não encontrada");
        }
    }

    @Transactional
    public void ativar(String nome) {
        try {
            RegiaoEnum.valueOf(nome);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Região não encontrada");
        }
    }

    @Transactional
    public void desativar(String nome) {
        try {
            RegiaoEnum.valueOf(nome);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Região não encontrada");
        }
    }
} 