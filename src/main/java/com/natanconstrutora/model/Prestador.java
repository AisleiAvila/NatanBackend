package com.natanconstrutora.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "prestadores")
public class Prestador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String cpf;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Column(length = 500)
    private String descricao;

    private Double avaliacaoMedia;

    private boolean ativo = true;
} 