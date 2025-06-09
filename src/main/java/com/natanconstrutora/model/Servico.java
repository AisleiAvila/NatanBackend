package com.natanconstrutora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "servicos")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @Size(max = 500)
    private String descricao;

    @PositiveOrZero
    private BigDecimal precoTabelado;

    private String imagemUrl;

    @ElementCollection(targetClass = Regiao.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "servico_regioes", joinColumns = @JoinColumn(name = "servico_id"))
    @Column(name = "regiao")
    private Set<Regiao> regioesAtendimento;

    private Boolean ativo = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Constructors
    public Servico() {}

    public Servico(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPrecoTabelado() { return precoTabelado; }
    public void setPrecoTabelado(BigDecimal precoTabelado) { this.precoTabelado = precoTabelado; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public Set<Regiao> getRegioesAtendimento() { return regioesAtendimento; }
    public void setRegioesAtendimento(Set<Regiao> regioesAtendimento) { this.regioesAtendimento = regioesAtendimento; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}