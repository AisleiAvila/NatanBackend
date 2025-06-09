package com.natanconstrutora.service;

import com.natanconstrutora.model.Usuario;
import com.natanconstrutora.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional
    public Usuario criar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(id)) {
            throw new RuntimeException("Email já cadastrado");
        }

        usuario.setId(id);
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        } else {
            usuario.setSenha(usuarioRepository.findById(id).get().getSenha());
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public void ativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void desativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new RuntimeException("Senha atual incorreta");
        }

        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
    }

    public List<Usuario> buscarPorPerfil(String perfil) {
        return usuarioRepository.findByPerfisNome(perfil);
    }
} 