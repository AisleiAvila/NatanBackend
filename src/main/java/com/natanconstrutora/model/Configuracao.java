package com.natanconstrutora.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "configuracoes")
public class Configuracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String chave;

    @Column(nullable = false)
    private String valor;

    @Column(length = 500)
    private String descricao;
} 