
package com.natanconstrutora.repository;

import com.natanconstrutora.model.Servico;
import com.natanconstrutora.model.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByRegioesContaining(Regiao regiao);
    List<Servico> findByAtivoTrue();
}
