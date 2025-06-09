package com.natanconstrutora.service;

import com.natanconstrutora.model.Notificacao;
import com.natanconstrutora.model.Usuario;
import com.natanconstrutora.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public List<Notificacao> listarTodas() {
        return notificacaoRepository.findAll();
    }

    public Optional<Notificacao> buscarPorId(Long id) {
        return notificacaoRepository.findById(id);
    }

    public List<Notificacao> buscarPorUsuario(Usuario usuario) {
        return notificacaoRepository.findByUsuario(usuario);
    }

    public List<Notificacao> buscarNaoLidas() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return notificacaoRepository.findByUsuarioAndLidaFalse(usuario);
    }

    public List<Notificacao> buscarNaoLidasPorUsuario(Usuario usuario) {
        return notificacaoRepository.findByUsuarioAndLidaFalse(usuario);
    }

    @Transactional
    public Notificacao criar(Notificacao notificacao) {
        notificacao.setDataCriacao(LocalDateTime.now());
        notificacao.setLida(false);
        return notificacaoRepository.save(notificacao);
    }

    @Transactional
    public Optional<Notificacao> marcarComoLida(Long id) {
        return notificacaoRepository.findById(id)
                .map(notificacao -> {
                    notificacao.setLida(true);
                    notificacao.setDataLeitura(LocalDateTime.now());
                    return notificacaoRepository.save(notificacao);
                });
    }

    @Transactional
    public void marcarTodasComoLidas() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        marcarTodasComoLidas(usuario);
    }

    @Transactional
    public void marcarTodasComoLidas(Usuario usuario) {
        List<Notificacao> notificacoes = notificacaoRepository.findByUsuarioAndLidaFalse(usuario);
        LocalDateTime agora = LocalDateTime.now();
        notificacoes.forEach(notificacao -> {
            notificacao.setLida(true);
            notificacao.setDataLeitura(agora);
        });
        notificacaoRepository.saveAll(notificacoes);
    }

    @Transactional
    public boolean excluir(Long id) {
        if (notificacaoRepository.existsById(id)) {
            notificacaoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public void excluirTodas() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        excluirTodas(usuario);
    }

    @Transactional
    public void excluirTodas(Usuario usuario) {
        List<Notificacao> notificacoes = notificacaoRepository.findByUsuario(usuario);
        notificacaoRepository.deleteAll(notificacoes);
    }
} 