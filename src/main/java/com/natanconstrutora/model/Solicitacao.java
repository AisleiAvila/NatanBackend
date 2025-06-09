package com.natanconstrutora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "solicitacoes")
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private User cliente;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestador_id")
    private User prestador;

    @NotBlank
    @Size(max = 1000)
    private String descricaoProblema;

    @NotBlank
    @Size(max = 200)
    private String endereco;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RegiaoEnum regiao;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status = StatusSolicitacao.AGUARDANDO_ORCAMENTO;

    @OneToOne(mappedBy = "solicitacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Orcamento orcamento;

    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImagemServico> imagens = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Avaliacao avaliacao;

    private LocalDateTime dataAgendamento;

    private LocalDateTime dataConclusao;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Constructors
    public Solicitacao() {}

    public Solicitacao(User cliente, Servico servico, String descricaoProblema, String endereco, RegiaoEnum regiao) {
        this.cliente = cliente;
        this.servico = servico;
        this.descricaoProblema = descricaoProblema;
        this.endereco = endereco;
        this.regiao = regiao;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getCliente() { return cliente; }
    public void setCliente(User cliente) { this.cliente = cliente; }

    public Servico getServico() { return servico; }
    public void setServico(Servico servico) { this.servico = servico; }

    public User getPrestador() { return prestador; }
    public void setPrestador(User prestador) { this.prestador = prestador; }

    public String getDescricaoProblema() { return descricaoProblema; }
    public void setDescricaoProblema(String descricaoProblema) { this.descricaoProblema = descricaoProblema; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public RegiaoEnum getRegiao() { return regiao; }
    public void setRegiao(RegiaoEnum regiao) { this.regiao = regiao; }

    public StatusSolicitacao getStatus() { return status; }
    public void setStatus(StatusSolicitacao status) { this.status = status; }

    public Orcamento getOrcamento() { return orcamento; }
    public void setOrcamento(Orcamento orcamento) { this.orcamento = orcamento; }

    public List<ImagemServico> getImagens() { return imagens; }
    public void setImagens(List<ImagemServico> imagens) { this.imagens = imagens; }

    public Avaliacao getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Avaliacao avaliacao) { this.avaliacao = avaliacao; }

    public LocalDateTime getDataAgendamento() { return dataAgendamento; }
    public void setDataAgendamento(LocalDateTime dataAgendamento) { this.dataAgendamento = dataAgendamento; }

    public LocalDateTime getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDateTime dataConclusao) { this.dataConclusao = dataConclusao; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
