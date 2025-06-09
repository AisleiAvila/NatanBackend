
package com.natanconstrutora.service;

import com.natanconstrutora.model.Servico;
import com.natanconstrutora.model.Regiao;
import com.natanconstrutora.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public List<Servico> buscarTodos() {
        return servicoRepository.findAll();
    }

    public List<Servico> buscarAtivos() {
        return servicoRepository.findByAtivoTrue();
    }

    public List<Servico> buscarPorRegiao(Regiao regiao) {
        return servicoRepository.findByRegioesContaining(regiao);
    }

    public Optional<Servico> buscarPorId(Long id) {
        return servicoRepository.findById(id);
    }

    public Servico salvar(Servico servico) {
        return servicoRepository.save(servico);
    }

    public void deletar(Long id) {
        servicoRepository.deleteById(id);
    }
}
