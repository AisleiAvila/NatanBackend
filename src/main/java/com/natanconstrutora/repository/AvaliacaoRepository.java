
package com.natanconstrutora.repository;

import com.natanconstrutora.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    Optional<Avaliacao> findBySolicitacaoId(Long solicitacaoId);
    List<Avaliacao> findByPrestadorId(Long prestadorId);
    List<Avaliacao> findByClienteId(Long clienteId);
    
    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.prestador.id = :prestadorId")
    Double findAverageNotaByPrestadorId(@Param("prestadorId") Long prestadorId);
}
