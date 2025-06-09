
package com.natanconstrutora.repository;

import com.natanconstrutora.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
    Optional<Orcamento> findBySolicitacaoId(Long solicitacaoId);
}
