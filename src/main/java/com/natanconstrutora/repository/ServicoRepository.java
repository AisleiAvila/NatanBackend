package com.natanconstrutora.repository;

import com.natanconstrutora.model.Servico;
import com.natanconstrutora.model.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByAtivoTrue();

    @Query("SELECT s FROM Servico s JOIN s.regioesAtendimento r WHERE r = :regiao AND s.ativo = true")
    List<Servico> findByRegiaoAndAtivoTrue(@Param("regiao") Regiao regiao);
}