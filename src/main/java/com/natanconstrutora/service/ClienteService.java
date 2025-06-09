package com.natanconstrutora.service;

import com.natanconstrutora.model.Cliente;
import com.natanconstrutora.model.RegiaoEnum;
import com.natanconstrutora.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Cliente> buscarPorRegiao(RegiaoEnum regiao) {
        return clienteRepository.findByRegiao(regiao);
    }

    @Transactional
    public Cliente criar(Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> atualizar(Long id, Cliente cliente) {
        return clienteRepository.findById(id)
            .map(existingCliente -> {
                cliente.setId(id);
                return clienteRepository.save(cliente);
            });
    }

    @Transactional
    public void excluir(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado");
        }
        clienteRepository.deleteById(id);
    }

    @Transactional
    public void ativar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.setAtivo(true);
        clienteRepository.save(cliente);
    }

    @Transactional
    public void desativar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.setAtivo(false);
        clienteRepository.save(cliente);
    }

    public long contarClientesPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return clienteRepository.countByDataCadastroBetween(inicio, fim);
    }

    public long contarClientesAtivos() {
        return clienteRepository.countByAtivoTrue();
    }

    public List<Map<String, Object>> buscarClientesPorRegiao(LocalDateTime inicio, LocalDateTime fim) {
        return clienteRepository.findClientesPorRegiao(inicio, fim);
    }

    public List<Map<String, Object>> contarClientesPorRegiao(LocalDateTime inicio, LocalDateTime fim) {
        return clienteRepository.findClientesPorRegiao(inicio, fim);
    }
} 