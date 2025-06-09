package com.natanconstrutora.repository;

import com.natanconstrutora.model.Servico;
import com.natanconstrutora.model.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByAtivoTrue();

    @Query("SELECT s FROM Servico s JOIN s.regioesAtendimento r WHERE r = :regiao AND s.ativo = true")
    List<Servico> findByRegiaoAndAtivoTrue(@Param("regiao") Regiao regiao);
    
    @Query("SELECT s FROM Servico s JOIN s.regioesAtendimento r WHERE r = :regiao")
    List<Servico> findByRegioesContaining(@Param("regiao") Regiao regiao);

    List<Servico> findByClienteId(Long clienteId);
    List<Servico> findByPrestadorId(Long prestadorId);
    List<Servico> findByCategoriaId(Long categoriaId);
    List<Servico> findByStatus(String status);
    List<Servico> findByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Servico> findByValorBetween(Double valorMinimo, Double valorMaximo);

    Long countByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT s.prestador.id as prestadorId, s.prestador.nome as prestadorNome, COUNT(s) as total " +
           "FROM Servico s " +
           "WHERE s.dataCriacao BETWEEN :inicio AND :fim " +
           "GROUP BY s.prestador.id, s.prestador.nome")
    List<Map<String, Object>> findServicosPorPrestador(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    @Query("SELECT s.categoria.id as categoriaId, s.categoria.nome as categoriaNome, COUNT(s) as total " +
           "FROM Servico s " +
           "WHERE s.dataCriacao BETWEEN :inicio AND :fim " +
           "GROUP BY s.categoria.id, s.categoria.nome")
    List<Map<String, Object>> findServicosPorCategoria(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    Long countByPrestadorIdAndDataCriacaoBetween(Long prestadorId, LocalDateTime inicio, LocalDateTime fim);
}