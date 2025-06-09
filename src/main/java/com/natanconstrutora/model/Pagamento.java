package com.natanconstrutora.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pagamentos")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime dataPagamento;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
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
} 