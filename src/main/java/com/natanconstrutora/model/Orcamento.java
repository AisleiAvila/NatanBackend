
package com.natanconstrutora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orcamentos")
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;

    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemOrcamento> itens = new ArrayList<>();

    @NotNull
    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    @NotNull
    @PositiveOrZero
    @Column(precision = 5, scale = 4)
    private BigDecimal taxaIva;

    @NotNull
    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal valorIva;

    @NotNull
    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    @NotNull
    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal valorPrestador;

    @NotNull
    private LocalDateTime dataValidade;

    private Boolean aprovado = false;

    private LocalDateTime dataAprovacao;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Constructors
    public Orcamento() {}

    public Orcamento(Solicitacao solicitacao, BigDecimal taxaIva, LocalDateTime dataValidade) {
        this.solicitacao = solicitacao;
        this.taxaIva = taxaIva;
        this.dataValidade = dataValidade;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Solicitacao getSolicitacao() { return solicitacao; }
    public void setSolicitacao(Solicitacao solicitacao) { this.solicitacao = solicitacao; }

    public List<ItemOrcamento> getItens() { return itens; }
    public void setItens(List<ItemOrcamento> itens) { this.itens = itens; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getTaxaIva() { return taxaIva; }
    public void setTaxaIva(BigDecimal taxaIva) { this.taxaIva = taxaIva; }

    public BigDecimal getValorIva() { return valorIva; }
    public void setValorIva(BigDecimal valorIva) { this.valorIva = valorIva; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getValorPrestador() { return valorPrestador; }
    public void setValorPrestador(BigDecimal valorPrestador) { this.valorPrestador = valorPrestador; }

    public LocalDateTime getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDateTime dataValidade) { this.dataValidade = dataValidade; }

    public Boolean getAprovado() { return aprovado; }
    public void setAprovado(Boolean aprovado) { this.aprovado = aprovado; }

    public LocalDateTime getDataAprovacao() { return dataAprovacao; }
    public void setDataAprovacao(LocalDateTime dataAprovacao) { this.dataAprovacao = dataAprovacao; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
