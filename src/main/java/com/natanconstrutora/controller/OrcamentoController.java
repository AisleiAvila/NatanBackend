
package com.natanconstrutora.controller;

import com.natanconstrutora.model.Orcamento;
import com.natanconstrutora.service.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/orcamentos")
public class OrcamentoController {

    @Autowired
    private OrcamentoService orcamentoService;

    @GetMapping
    public List<Orcamento> listarTodos() {
        return orcamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orcamento> buscarPorId(@PathVariable Long id) {
        Optional<Orcamento> orcamento = orcamentoService.buscarPorId(id);
        return orcamento.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/solicitacao/{solicitacaoId}")
    public ResponseEntity<Orcamento> buscarPorSolicitacao(@PathVariable Long solicitacaoId) {
        Optional<Orcamento> orcamento = orcamentoService.buscarPorSolicitacao(solicitacaoId);
        return orcamento.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{orcamentoId}/itens")
    public ResponseEntity<Orcamento> adicionarItem(
            @PathVariable Long orcamentoId,
            @RequestParam String descricao,
            @RequestParam Integer quantidade,
            @RequestParam BigDecimal precoUnitario) {
        try {
            Orcamento orcamento = orcamentoService.adicionarItem(orcamentoId, descricao, quantidade, precoUnitario);
            return ResponseEntity.ok(orcamento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{orcamentoId}/taxa-iva")
    public ResponseEntity<Orcamento> atualizarTaxaIva(
            @PathVariable Long orcamentoId,
            @RequestParam BigDecimal novaTaxa) {
        try {
            Orcamento orcamento = orcamentoService.atualizarTaxaIva(orcamentoId, novaTaxa);
            return ResponseEntity.ok(orcamento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{orcamentoId}/aprovar")
    public ResponseEntity<Orcamento> aprovarOrcamento(@PathVariable Long orcamentoId) {
        try {
            Orcamento orcamento = orcamentoService.aprovarOrcamento(orcamentoId);
            return ResponseEntity.ok(orcamento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
