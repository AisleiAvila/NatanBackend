package com.natanconstrutora.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

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

    @Column(nullable = false, unique = true)
    private String cpf;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Column(length = 500)
    private String descricao;

    private Double avaliacaoMedia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegiaoEnum regiao;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    private boolean ativo = true;

    @ManyToMany
    @JoinTable(
        name = "prestador_categoria",
        joinColumns = @JoinColumn(name = "prestador_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias;
} 