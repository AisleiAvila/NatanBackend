package com.natanconstrutora.service;

import com.natanconstrutora.model.*;
import com.natanconstrutora.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    public List<Solicitacao> buscarTodas() {
        return solicitacaoRepository.findAll();
    }

    public Optional<Solicitacao> buscarPorId(Long id) {
        return solicitacaoRepository.findById(id);
    }

    public List<Solicitacao> buscarPorStatus(StatusSolicitacao status) {
        return solicitacaoRepository.findByStatus(status);
    }

    public List<Solicitacao> buscarPorRegiao(RegiaoEnum regiao) {
        return solicitacaoRepository.findByRegiao(regiao);
    }

    public List<Solicitacao> buscarPorCliente(Long clienteId) {
        return solicitacaoRepository.findByClienteId(clienteId);
    }

    public List<Solicitacao> buscarPorPrestador(Long prestadorId) {
        return solicitacaoRepository.findByPrestadorId(prestadorId);
    }

    @Transactional
    public Solicitacao criarSolicitacao(Long clienteId, Long servicoId, String descricaoProblema, String endereco, RegiaoEnum regiao) {
        User cliente = userRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        Solicitacao solicitacao = new Solicitacao(cliente, servico, descricaoProblema, endereco, regiao);
        return solicitacaoRepository.save(solicitacao);
    }

    @Transactional
    public Solicitacao atualizarStatus(Long id, StatusSolicitacao novoStatus) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        
        solicitacao.setStatus(novoStatus);
        
        if (novoStatus == StatusSolicitacao.CONCLUIDO) {
            solicitacao.setDataConclusao(LocalDateTime.now());
        }
        
        return solicitacaoRepository.save(solicitacao);
    }

    @Transactional
    public Solicitacao atribuirPrestador(Long solicitacaoId, Long prestadorId) {
        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        
        User prestador = userRepository.findById(prestadorId)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
        
        // Verificar se o prestador atende a região
        if (prestador.getRegiao() != solicitacao.getRegiao()) {
            throw new RuntimeException("Prestador não atende esta região");
        }
        
        solicitacao.setPrestador(prestador);
        solicitacao.setStatus(StatusSolicitacao.EM_ANDAMENTO);
        
        return solicitacaoRepository.save(solicitacao);
    }

    public List<User> sugerirPrestadores(Long solicitacaoId) {
        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        
        return userRepository.findPrestadoresByRegiao(RoleName.ROLE_PRESTADOR, solicitacao.getRegiao());
    }

    public List<Map<String, Object>> contarSolicitacoesPorRegiao(LocalDateTime inicio, LocalDateTime fim) {
        return solicitacaoRepository.findSolicitacoesPorRegiao(inicio, fim);
    }

    public List<Map<String, Object>> contarSolicitacoesPorStatus() {
        return solicitacaoRepository.findSolicitacoesPorStatus();
    }
}
