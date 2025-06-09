package com.natanconstrutora.repository;

import com.natanconstrutora.model.Solicitacao;
import com.natanconstrutora.model.StatusSolicitacao;
import com.natanconstrutora.model.RegiaoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    List<Solicitacao> findByStatus(StatusSolicitacao status);
    List<Solicitacao> findByRegiao(RegiaoEnum regiao);
    List<Solicitacao> findByClienteId(Long clienteId);
    List<Solicitacao> findByPrestadorId(Long prestadorId);
    List<Solicitacao> findByCreatedAtBetween(LocalDateTime inicio, LocalDateTime fim);
    
    @Query("SELECT s FROM Solicitacao s WHERE s.status = :status AND s.regiao = :regiao")
    List<Solicitacao> findByStatusAndRegiao(@Param("status") StatusSolicitacao status, @Param("regiao") RegiaoEnum regiao);
    
    @Query("SELECT COUNT(s) FROM Solicitacao s WHERE s.status = :status")
    Long countByStatus(@Param("status") StatusSolicitacao status);

    long countByCreatedAtBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT s.regiao as regiao, COUNT(s) as total FROM Solicitacao s WHERE s.createdAt BETWEEN :inicio AND :fim GROUP BY s.regiao")
    List<Map<String, Object>> findSolicitacoesPorRegiao(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT s.status as status, COUNT(s) as total FROM Solicitacao s WHERE s.createdAt BETWEEN :inicio AND :fim GROUP BY s.status")
    List<Map<String, Object>> findSolicitacoesPorStatus(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT s.status as status, COUNT(s) as total FROM Solicitacao s GROUP BY s.status")
    List<Map<String, Object>> findSolicitacoesPorStatus();
}
