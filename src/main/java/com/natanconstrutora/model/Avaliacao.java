
package com.natanconstrutora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacoes")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private User cliente;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestador_id")
    private User prestador;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer nota;

    @Size(max = 1000)
    private String comentario;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Constructors
    public Avaliacao() {}

    public Avaliacao(Solicitacao solicitacao, User cliente, User prestador, Integer nota) {
        this.solicitacao = solicitacao;
        this.cliente = cliente;
        this.prestador = prestador;
        this.nota = nota;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Solicitacao getSolicitacao() { return solicitacao; }
    public void setSolicitacao(Solicitacao solicitacao) { this.solicitacao = solicitacao; }

    public User getCliente() { return cliente; }
    public void setCliente(User cliente) { this.cliente = cliente; }

    public User getPrestador() { return prestador; }
    public void setPrestador(User prestador) { this.prestador = prestador; }

    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
