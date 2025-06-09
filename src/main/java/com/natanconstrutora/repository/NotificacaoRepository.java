package com.natanconstrutora.repository;

import com.natanconstrutora.model.Notificacao;
import com.natanconstrutora.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByUsuario(Usuario usuario);
    List<Notificacao> findByUsuarioAndLidaFalse(Usuario usuario);
    
    @Modifying
    @Query("UPDATE Notificacao n SET n.lida = true")
    void marcarTodasComoLidas();
} 