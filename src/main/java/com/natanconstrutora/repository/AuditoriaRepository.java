package com.natanconstrutora.repository;

import com.natanconstrutora.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    List<Auditoria> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);
    List<Auditoria> findByUsuarioId(Long usuarioId);
    List<Auditoria> findByTipo(String tipo);
    void deleteByDataBefore(LocalDate dataLimite);
} 