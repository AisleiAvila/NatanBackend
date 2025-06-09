package com.natanconstrutora.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "avaliacoes")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    private Prestador prestador;

    @Column(nullable = false)
    private Integer nota;

    @Column(length = 1000)
    private String comentario;

    @Column(nullable = false)
    private Boolean ativo = true;
}
