package com.natanconstrutora.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "permissoes")
public class Permissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(length = 500)
    private String descricao;

    private boolean ativo = true;
} 