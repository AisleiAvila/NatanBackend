
package com.natanconstrutora.controller;

import com.natanconstrutora.model.*;
import com.natanconstrutora.service.SolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/solicitacoes")
@PreAuthorize("hasRole('ADMIN')")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @GetMapping
    public List<Solicitacao> listarTodas() {
        return solicitacaoService.buscarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> buscarPorId(@PathVariable Long id) {
        Optional<Solicitacao> solicitacao = solicitacaoService.buscarPorId(id);
        return solicitacao.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public List<Solicitacao> buscarPorStatus(@PathVariable StatusSolicitacao status) {
        return solicitacaoService.buscarPorStatus(status);
    }

    @GetMapping("/regiao/{regiao}")
    public List<Solicitacao> buscarPorRegiao(@PathVariable Regiao regiao) {
        return solicitacaoService.buscarPorRegiao(regiao);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Solicitacao> buscarPorCliente(@PathVariable Long clienteId) {
        return solicitacaoService.buscarPorCliente(clienteId);
    }

    @GetMapping("/prestador/{prestadorId}")
    public List<Solicitacao> buscarPorPrestador(@PathVariable Long prestadorId) {
        return solicitacaoService.buscarPorPrestador(prestadorId);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Solicitacao> atualizarStatus(
            @PathVariable Long id, 
            @RequestParam StatusSolicitacao novoStatus) {
        try {
            Solicitacao solicitacaoAtualizada = solicitacaoService.atualizarStatus(id, novoStatus);
            return ResponseEntity.ok(solicitacaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/prestador")
    public ResponseEntity<Solicitacao> atribuirPrestador(
            @PathVariable Long id, 
            @RequestParam Long prestadorId) {
        try {
            Solicitacao solicitacaoAtualizada = solicitacaoService.atribuirPrestador(id, prestadorId);
            return ResponseEntity.ok(solicitacaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/prestadores-sugeridos")
    public List<User> sugerirPrestadores(@PathVariable Long id) {
        return solicitacaoService.sugerirPrestadores(id);
    }
}
