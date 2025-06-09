package com.natanconstrutora.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "prestador_id", nullable = false)
    private User prestador;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento status;

    @Column(length = 100)
    private String codigoTransacao;

    @Column(length = 500)
    private String observacao;

    @PrePersist
    protected void onCreate() {
        dataPagamento = LocalDateTime.now();
    }

    public enum StatusPagamento {
        PENDENTE,
        APROVADO,
        RECUSADO,
        CANCELADO
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Servico getServico() { return servico; }
    public void setServico(Servico servico) { this.servico = servico; }

    public User getPrestador() { return prestador; }
    public void setPrestador(User prestador) { this.prestador = prestador; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }

    public StatusPagamento getStatus() { return status; }
    public void setStatus(StatusPagamento status) { this.status = status; }
} 