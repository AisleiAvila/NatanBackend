package com.natanconstrutora.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "auditoria")
public class Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private LocalDate data;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "entidade_id")
    private Long entidadeId;

    @Column(name = "entidade_tipo")
    private String entidadeTipo;
} 