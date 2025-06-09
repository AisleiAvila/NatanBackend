
package com.natanconstrutora.controller;

import com.natanconstrutora.model.Solicitacao;
import com.natanconstrutora.model.StatusSolicitacao;
import com.natanconstrutora.model.Regiao;
import com.natanconstrutora.repository.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/solicitacoes")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @GetMapping
    public List<Solicitacao> listarTodas() {
        return solicitacaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> buscarPorId(@PathVariable Long id) {
        Optional<Solicitacao> solicitacao = solicitacaoRepository.findById(id);
        return solicitacao.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public List<Solicitacao> buscarPorStatus(@PathVariable StatusSolicitacao status) {
        return solicitacaoRepository.findByStatus(status);
    }

    @GetMapping("/regiao/{regiao}")
    public List<Solicitacao> buscarPorRegiao(@PathVariable Regiao regiao) {
        return solicitacaoRepository.findByRegiao(regiao);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Solicitacao> buscarPorCliente(@PathVariable Long clienteId) {
        return solicitacaoRepository.findByClienteId(clienteId);
    }

    @GetMapping("/prestador/{prestadorId}")
    public List<Solicitacao> buscarPorPrestador(@PathVariable Long prestadorId) {
        return solicitacaoRepository.findByPrestadorId(prestadorId);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Solicitacao> atualizarStatus(
            @PathVariable Long id, 
            @RequestParam StatusSolicitacao novoStatus) {
        Optional<Solicitacao> solicitacaoOpt = solicitacaoRepository.findById(id);
        if (solicitacaoOpt.isPresent()) {
            Solicitacao solicitacao = solicitacaoOpt.get();
            solicitacao.setStatus(novoStatus);
            Solicitacao solicitacaoAtualizada = solicitacaoRepository.save(solicitacao);
            return ResponseEntity.ok(solicitacaoAtualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/prestador")
    public ResponseEntity<Solicitacao> atribuirPrestador(
            @PathVariable Long id, 
            @RequestParam Long prestadorId) {
        // Este método precisaria de validação para verificar se o prestador existe
        // e se atende a região da solicitação
        Optional<Solicitacao> solicitacaoOpt = solicitacaoRepository.findById(id);
        if (solicitacaoOpt.isPresent()) {
            Solicitacao solicitacao = solicitacaoOpt.get();
            // Aqui deveria buscar o prestador pelo ID e validar
            // solicitacao.setPrestador(prestador);
            Solicitacao solicitacaoAtualizada = solicitacaoRepository.save(solicitacao);
            return ResponseEntity.ok(solicitacaoAtualizada);
        }
        return ResponseEntity.notFound().build();
    }
}
