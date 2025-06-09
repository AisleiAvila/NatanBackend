package com.natanconstrutora.repository;

import com.natanconstrutora.model.Prestador;
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
public interface PrestadorRepository extends JpaRepository<Prestador, Long> {
    Optional<Prestador> findByEmail(String email);
    Optional<Prestador> findByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    List<Prestador> findByNomeContainingIgnoreCase(String nome);
    List<Prestador> findByAvaliacaoMediaGreaterThanEqual(Double avaliacaoMedia);
    List<Prestador> findByCategoriasId(Long categoriaId);
    long countByDataCadastroBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Prestador> findByRegiao(RegiaoEnum regiao);

    @Query("SELECT c.nome as categoria, COUNT(p) as total FROM Prestador p JOIN p.categorias c WHERE p.dataCadastro BETWEEN :inicio AND :fim GROUP BY c.nome")
    List<Map<String, Object>> findPrestadoresPorCategoria(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT p.regiao as regiao, COUNT(p) as total FROM Prestador p WHERE p.dataCadastro BETWEEN :inicio AND :fim GROUP BY p.regiao")
    List<Map<String, Object>> findPrestadoresPorRegiao(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
