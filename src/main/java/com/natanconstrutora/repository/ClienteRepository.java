package com.natanconstrutora.repository;

import com.natanconstrutora.model.Cliente;
import com.natanconstrutora.model.RegiaoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    List<Cliente> findByRegiao(RegiaoEnum regiao);
    long countByDataCadastroBetween(LocalDateTime inicio, LocalDateTime fim);
    long countByAtivoTrue();

    @Query("SELECT c.regiao as regiao, COUNT(c) as total FROM Cliente c WHERE c.dataCadastro BETWEEN :inicio AND :fim GROUP BY c.regiao")
    List<Map<String, Object>> findClientesPorRegiao(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
} 