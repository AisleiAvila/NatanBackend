package com.natanconstrutora.service;

import com.natanconstrutora.model.Endereco;
import com.natanconstrutora.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Endereco> listarTodos() {
        return enderecoRepository.findAll();
    }

    public Optional<Endereco> buscarPorId(Long id) {
        return enderecoRepository.findById(id);
    }

    public List<Endereco> buscarPorCep(String cep) {
        return enderecoRepository.findByCep(cep);
    }

    public List<Endereco> buscarPorCidade(String cidade) {
        return enderecoRepository.findByCidadeContainingIgnoreCase(cidade);
    }

    public List<Endereco> buscarPorEstado(String estado) {
        return enderecoRepository.findByEstado(estado);
    }

    @Transactional
    public Endereco criar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    @Transactional
    public Endereco atualizar(Long id, Endereco endereco) {
        if (!enderecoRepository.existsById(id)) {
            throw new RuntimeException("Endereço não encontrado");
        }
        endereco.setId(id);
        return enderecoRepository.save(endereco);
    }

    @Transactional
    public void excluir(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new RuntimeException("Endereço não encontrado");
        }
        enderecoRepository.deleteById(id);
    }

    public List<Endereco> buscarPorBairro(String bairro) {
        return enderecoRepository.findByBairroContainingIgnoreCase(bairro);
    }

    public List<Endereco> buscarPorLogradouro(String logradouro) {
        return enderecoRepository.findByLogradouroContainingIgnoreCase(logradouro);
    }

    public List<Endereco> listar() {
        return enderecoRepository.findAll();
    }

    public Optional<Endereco> buscar(Long id) {
        return enderecoRepository.findById(id);
    }
} 