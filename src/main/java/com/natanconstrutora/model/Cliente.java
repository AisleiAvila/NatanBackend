package com.natanconstrutora.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {
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

    private boolean ativo = true;

    @Enumerated(EnumType.STRING)
    private RegiaoEnum regiao;

    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;
} 