
package com.natanconstrutora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    @OneToOne
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;

    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemOrcamento> itens = new ArrayList<>();

    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @PositiveOrZero
    @Column(precision = 5, scale = 2)
    private BigDecimal taxaIva = new BigDecimal("23.00"); // Taxa padrão de IVA em Portugal

    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal valorIva = BigDecimal.ZERO;

    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @PositiveOrZero
    @Column(precision = 5, scale = 2)
    private BigDecimal percentualPrestador = new BigDecimal("70.00"); // 70% para o prestador

    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal valorPrestador = BigDecimal.ZERO;

    private LocalDate dataValidade;

    private Boolean aprovado = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Constructors
    public Orcamento() {}

    public Orcamento(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    // Método para calcular totais
    public void calcularTotais() {
        this.subtotal = itens.stream()
                .map(ItemOrcamento::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        this.valorIva = subtotal.multiply(taxaIva).divide(new BigDecimal("100"));
        this.total = subtotal.add(valorIva);
        this.valorPrestador = subtotal.multiply(percentualPrestador).divide(new BigDecimal("100"));
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

    public BigDecimal getPercentualPrestador() { return percentualPrestador; }
    public void setPercentualPrestador(BigDecimal percentualPrestador) { this.percentualPrestador = percentualPrestador; }

    public BigDecimal getValorPrestador() { return valorPrestador; }
    public void setValorPrestador(BigDecimal valorPrestador) { this.valorPrestador = valorPrestador; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }

    public Boolean getAprovado() { return aprovado; }
    public void setAprovado(Boolean aprovado) { this.aprovado = aprovado; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
