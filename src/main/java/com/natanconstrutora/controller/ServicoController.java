
package com.natanconstrutora.controller;

import com.natanconstrutora.model.Servico;
import com.natanconstrutora.model.Regiao;
import com.natanconstrutora.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @GetMapping
    public List<Servico> listarTodos() {
        return servicoService.buscarAtivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
        Optional<Servico> servico = servicoService.buscarPorId(id);
        return servico.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/regiao/{regiao}")
    public List<Servico> buscarPorRegiao(@PathVariable Regiao regiao) {
        return servicoService.buscarPorRegiao(regiao);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Servico criar(@Valid @RequestBody Servico servico) {
        return servicoService.salvar(servico);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Servico> atualizar(@PathVariable Long id, @Valid @RequestBody Servico servicoAtualizado) {
        Optional<Servico> servicoExistente = servicoService.buscarPorId(id);
        if (servicoExistente.isPresent()) {
            servicoAtualizado.setId(id);
            return ResponseEntity.ok(servicoService.salvar(servicoAtualizado));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Servico> servico = servicoService.buscarPorId(id);
        if (servico.isPresent()) {
            servicoService.deletar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
