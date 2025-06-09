package com.natanconstrutora.repository;

import com.natanconstrutora.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByCep(String cep);
    List<Endereco> findByCidadeContainingIgnoreCase(String cidade);
    List<Endereco> findByEstado(String estado);
    List<Endereco> findByBairroContainingIgnoreCase(String bairro);
    List<Endereco> findByLogradouroContainingIgnoreCase(String logradouro);
} 