package com.natanconstrutora.repository;

import com.natanconstrutora.model.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RegiaoRepository extends JpaRepository<Regiao, Long> {
    Optional<Regiao> findByNome(String nome);
    boolean existsByNome(String nome);
} 