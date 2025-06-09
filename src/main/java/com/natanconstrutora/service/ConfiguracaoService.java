package com.natanconstrutora.service;

import com.natanconstrutora.model.Configuracao;
import com.natanconstrutora.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    public List<Configuracao> listarTodas() {
        return configuracaoRepository.findAll();
    }

    public Optional<Configuracao> buscarPorId(Long id) {
        return configuracaoRepository.findById(id);
    }

    public Optional<Configuracao> buscarPorChave(String chave) {
        return configuracaoRepository.findByChave(chave);
    }

    @Transactional
    public Configuracao criar(Configuracao configuracao) {
        if (configuracaoRepository.findByChave(configuracao.getChave()).isPresent()) {
            throw new RuntimeException("Chave já existe");
        }
        return configuracaoRepository.save(configuracao);
    }

    @Transactional
    public Configuracao atualizar(Long id, Configuracao configuracao) {
        if (!configuracaoRepository.existsById(id)) {
            throw new RuntimeException("Configuração não encontrada");
        }

        Optional<Configuracao> configuracaoExistente = configuracaoRepository.findByChave(configuracao.getChave());
        if (configuracaoExistente.isPresent() && !configuracaoExistente.get().getId().equals(id)) {
            throw new RuntimeException("Chave já existe");
        }

        configuracao.setId(id);
        return configuracaoRepository.save(configuracao);
    }

    @Transactional
    public void excluir(Long id) {
        if (!configuracaoRepository.existsById(id)) {
            throw new RuntimeException("Configuração não encontrada");
        }
        configuracaoRepository.deleteById(id);
    }

    @Transactional
    public void atualizarValor(String chave, String valor) {
        Configuracao configuracao = configuracaoRepository.findByChave(chave)
            .orElseThrow(() -> new RuntimeException("Configuração não encontrada"));
        configuracao.setValor(valor);
        configuracaoRepository.save(configuracao);
    }

    public List<Configuracao> obterConfiguracoes() {
        return configuracaoRepository.findAll();
    }

    public Configuracao atualizarConfiguracao(String chave, String valor) {
        Configuracao configuracao = configuracaoRepository.findByChave(chave)
            .orElseThrow(() -> new RuntimeException("Configuração não encontrada"));
        configuracao.setValor(valor);
        return configuracaoRepository.save(configuracao);
    }

    public Configuracao obterConfiguracao(String chave) {
        return configuracaoRepository.findByChave(chave)
            .orElseThrow(() -> new RuntimeException("Configuração não encontrada"));
    }

    public void resetarConfiguracoes() {
        configuracaoRepository.deleteAll();
        // Aqui você pode adicionar a lógica para recriar as configurações padrão
        Configuracao configPadrao = new Configuracao();
        configPadrao.setChave("config_padrao");
        configPadrao.setValor("valor_padrao");
        configuracaoRepository.save(configPadrao);
    }
} 