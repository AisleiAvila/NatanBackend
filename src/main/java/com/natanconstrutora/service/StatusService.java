package com.natanconstrutora.service;

import com.natanconstrutora.model.Status;
import com.natanconstrutora.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public List<Status> listarTodos() {
        return statusRepository.findAll();
    }

    public Optional<Status> buscarPorId(Long id) {
        return statusRepository.findById(id);
    }

    public Optional<Status> buscarPorNome(String nome) {
        return statusRepository.findByNome(nome);
    }

    @Transactional
    public Status criar(Status status) {
        if (statusRepository.existsByNome(status.getNome())) {
            throw new RuntimeException("Status já existe");
        }
        return statusRepository.save(status);
    }

    @Transactional
    public Optional<Status> atualizar(Long id, Status status) {
        if (!statusRepository.existsById(id)) {
            return Optional.empty();
        }

        Optional<Status> statusExistente = statusRepository.findByNome(status.getNome());
        if (statusExistente.isPresent() && !statusExistente.get().getId().equals(id)) {
            throw new RuntimeException("Nome de status já existe");
        }

        status.setId(id);
        return Optional.of(statusRepository.save(status));
    }

    @Transactional
    public boolean excluir(Long id) {
        if (!statusRepository.existsById(id)) {
            return false;
        }
        statusRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void ativar(Long id) {
        Status status = statusRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Status não encontrado"));
        status.setAtivo(true);
        statusRepository.save(status);
    }

    @Transactional
    public void desativar(Long id) {
        Status status = statusRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Status não encontrado"));
        status.setAtivo(false);
        statusRepository.save(status);
    }
} 