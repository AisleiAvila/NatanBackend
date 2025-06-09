package com.natanconstrutora.repository;

import com.natanconstrutora.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    Optional<Avaliacao> findBySolicitacaoId(Long solicitacaoId);
    List<Avaliacao> findByPrestadorId(Long prestadorId);
    List<Avaliacao> findByClienteId(Long clienteId);
    List<Avaliacao> findByServicoId(Long servicoId);
    
    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.prestador.id = :prestadorId")
    Double findAverageNotaByPrestadorId(@Param("prestadorId") Long prestadorId);
    
    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.prestador.id = :prestadorId")
    Double findAverageRatingByPrestadorId(@Param("prestadorId") Long prestadorId);

    @Query("SELECT AVG(a.nota) FROM Avaliacao a")
    Double findMediaAvaliacoes();

    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.dataCriacao BETWEEN :inicio AND :fim")
    Double findMediaAvaliacoesPorPeriodo(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.servico.prestador.id = :prestadorId AND a.dataCriacao BETWEEN :inicio AND :fim")
    Double findMediaAvaliacoesPorPrestadorId(
        @Param("prestadorId") Long prestadorId,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    Long countByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);
    Long countByPrestadorIdAndDataCriacaoBetween(Long prestadorId, LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT a.servico.prestador.id as prestadorId, a.servico.prestador.nome as prestadorNome, " +
           "COUNT(a) as total, AVG(a.nota) as media " +
           "FROM Avaliacao a " +
           "WHERE a.dataCriacao BETWEEN :inicio AND :fim " +
           "GROUP BY a.servico.prestador.id, a.servico.prestador.nome")
    List<Map<String, Object>> findAvaliacoesPorPrestador(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    @Query("SELECT a.servico.categoria.id as categoriaId, a.servico.categoria.nome as categoriaNome, " +
           "COUNT(a) as total, AVG(a.nota) as media " +
           "FROM Avaliacao a " +
           "WHERE a.dataCriacao BETWEEN :inicio AND :fim " +
           "GROUP BY a.servico.categoria.id, a.servico.categoria.nome")
    List<Map<String, Object>> findAvaliacoesPorCategoria(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );
}
