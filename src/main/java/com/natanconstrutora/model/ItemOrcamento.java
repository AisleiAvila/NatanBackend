
package com.natanconstrutora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_orcamento")
public class ItemOrcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orcamento_id")
    private Orcamento orcamento;

    @NotBlank
    @Size(max = 200)
    private String descricao;

    @NotNull
    @Positive
    @Column(precision = 10, scale = 2)
    private BigDecimal quantidade;

    @NotNull
    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @NotNull
    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    // Constructors
    public ItemOrcamento() {}

    public ItemOrcamento(Orcamento orcamento, String descricao, BigDecimal quantidade, BigDecimal precoUnitario) {
        this.orcamento = orcamento;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = quantidade.multiply(precoUnitario);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Orcamento getOrcamento() { return orcamento; }
    public void setOrcamento(Orcamento orcamento) { this.orcamento = orcamento; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
