
package com.natanconstrutora.repository;

import com.natanconstrutora.model.ImagemServico;
import com.natanconstrutora.model.TipoImagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemServicoRepository extends JpaRepository<ImagemServico, Long> {
    List<ImagemServico> findBySolicitacaoId(Long solicitacaoId);
    List<ImagemServico> findBySolicitacaoIdAndTipo(Long solicitacaoId, TipoImagem tipo);
}
