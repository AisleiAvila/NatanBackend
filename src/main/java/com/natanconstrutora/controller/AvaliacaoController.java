
package com.natanconstrutora.controller;

import com.natanconstrutora.model.Avaliacao;
import com.natanconstrutora.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @GetMapping("/prestador/{prestadorId}")
    public List<Avaliacao> buscarPorPrestador(@PathVariable Long prestadorId) {
        return avaliacaoRepository.findByPrestadorId(prestadorId);
    }

    @GetMapping("/solicitacao/{solicitacaoId}")
    public ResponseEntity<Avaliacao> buscarPorSolicitacao(@PathVariable Long solicitacaoId) {
        Optional<Avaliacao> avaliacao = avaliacaoRepository.findBySolicitacaoId(solicitacaoId);
        return avaliacao.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMIN')")
    public Avaliacao criar(@Valid @RequestBody Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    @GetMapping("/media/prestador/{prestadorId}")
    public ResponseEntity<Double> obterMediaPrestador(@PathVariable Long prestadorId) {
        Double media = avaliacaoRepository.findAverageRatingByPrestadorId(prestadorId);
        return ResponseEntity.ok(media != null ? media : 0.0);
    }
}
