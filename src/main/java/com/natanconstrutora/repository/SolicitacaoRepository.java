
package com.natanconstrutora.repository;

import com.natanconstrutora.model.Solicitacao;
import com.natanconstrutora.model.StatusSolicitacao;
import com.natanconstrutora.model.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    List<Solicitacao> findByStatus(StatusSolicitacao status);
    List<Solicitacao> findByRegiao(Regiao regiao);
    List<Solicitacao> findByClienteId(Long clienteId);
    List<Solicitacao> findByPrestadorId(Long prestadorId);
    
    @Query("SELECT s FROM Solicitacao s WHERE s.createdAt BETWEEN :startDate AND :endDate")
    List<Solicitacao> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                     @Param("endDate") LocalDateTime endDate);
}
