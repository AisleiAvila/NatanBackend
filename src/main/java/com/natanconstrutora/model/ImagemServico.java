
package com.natanconstrutora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "imagens_servico")
public class ImagemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;

    @NotBlank
    @Size(max = 255)
    private String nomeArquivo;

    @NotBlank
    @Size(max = 500)
    private String url;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoImagem tipo;

    @Size(max = 200)
    private String descricao;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Constructors
    public ImagemServico() {}

    public ImagemServico(Solicitacao solicitacao, String nomeArquivo, String url, TipoImagem tipo) {
        this.solicitacao = solicitacao;
        this.nomeArquivo = nomeArquivo;
        this.url = url;
        this.tipo = tipo;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Solicitacao getSolicitacao() { return solicitacao; }
    public void setSolicitacao(Solicitacao solicitacao) { this.solicitacao = solicitacao; }

    public String getNomeArquivo() { return nomeArquivo; }
    public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public TipoImagem getTipo() { return tipo; }
    public void setTipo(TipoImagem tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
