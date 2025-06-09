package com.natanconstrutora.service;

import com.natanconstrutora.model.Categoria;
import com.natanconstrutora.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Optional<Categoria> buscarPorNome(String nome) {
        return categoriaRepository.findByNome(nome);
    }

    @Transactional
    public Categoria criar(Categoria categoria) {
        if (categoriaRepository.existsByNome(categoria.getNome())) {
            throw new RuntimeException("Já existe uma categoria com este nome");
        }
        return categoriaRepository.save(categoria);
    }

    public Optional<Categoria> atualizar(Long id, Categoria categoria) {
        return categoriaRepository.findById(id)
            .map(existingCategoria -> {
                categoria.setId(id);
                return categoriaRepository.save(categoria);
            });
    }

    @Transactional
    public boolean excluir(Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 