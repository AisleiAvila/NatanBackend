package com.natanconstrutora.repository;

import com.natanconstrutora.model.Pagamento;
import com.natanconstrutora.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByServico(Servico servico);
    List<Pagamento> findByStatus(Pagamento.StatusPagamento status);
    List<Pagamento> findByDataPagamentoBetween(LocalDateTime inicio, LocalDateTime fim);
    long countByDataPagamentoBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Pagamento> findByServico_ClienteId(Long clienteId);
    List<Pagamento> findByServico_PrestadorId(Long prestadorId);

    @Query("SELECT p.prestador.id as prestadorId, p.prestador.nome as prestadorNome, SUM(p.valor) as total " +
           "FROM Pagamento p " +
           "WHERE p.dataPagamento BETWEEN :inicio AND :fim " +
           "GROUP BY p.prestador.id, p.prestador.nome")
    List<Map<String, Object>> findFaturamentoPorPrestador(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    @Query("SELECT p.servico.categoria.id as categoriaId, p.servico.categoria.nome as categoriaNome, SUM(p.valor) as total " +
           "FROM Pagamento p " +
           "WHERE p.dataPagamento BETWEEN :inicio AND :fim " +
           "GROUP BY p.servico.categoria.id, p.servico.categoria.nome")
    List<Map<String, Object>> findFaturamentoPorCategoria(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    @Query("SELECT SUM(p.valor) FROM Pagamento p")
    Double findFaturamentoTotal();

    @Query("SELECT SUM(p.valor) FROM Pagamento p WHERE p.dataPagamento BETWEEN :inicio AND :fim")
    Double findFaturamentoPorPeriodo(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    @Query("SELECT SUM(p.valor) FROM Pagamento p WHERE p.prestador.id = :prestadorId AND p.dataPagamento BETWEEN :inicio AND :fim")
    Double findFaturamentoPorPrestadorId(
        @Param("prestadorId") Long prestadorId,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    Long countByPrestadorIdAndDataPagamentoBetween(Long prestadorId, LocalDateTime inicio, LocalDateTime fim);
} 