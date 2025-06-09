
package com.natanconstrutora.service;

import com.natanconstrutora.model.Orcamento;
import com.natanconstrutora.model.ItemOrcamento;
import com.natanconstrutora.model.Solicitacao;
import com.natanconstrutora.repository.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrcamentoService {

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    public Orcamento criarOrcamento(Solicitacao solicitacao) {
        Orcamento orcamento = new Orcamento(solicitacao);
        orcamento.setDataValidade(LocalDate.now().plusDays(30)); // Válido por 30 dias
        return orcamentoRepository.save(orcamento);
    }

    public Orcamento adicionarItem(Long orcamentoId, String descricao, Integer quantidade, BigDecimal precoUnitario) {
        Optional<Orcamento> orcamentoOpt = orcamentoRepository.findById(orcamentoId);
        if (orcamentoOpt.isPresent()) {
            Orcamento orcamento = orcamentoOpt.get();
            ItemOrcamento item = new ItemOrcamento(orcamento, descricao, quantidade, precoUnitario);
            orcamento.getItens().add(item);
            orcamento.calcularTotais();
            return orcamentoRepository.save(orcamento);
        }
        throw new RuntimeException("Orçamento não encontrado");
    }

    public Orcamento atualizarTaxaIva(Long orcamentoId, BigDecimal novaTaxa) {
        Optional<Orcamento> orcamentoOpt = orcamentoRepository.findById(orcamentoId);
        if (orcamentoOpt.isPresent()) {
            Orcamento orcamento = orcamentoOpt.get();
            orcamento.setTaxaIva(novaTaxa);
            orcamento.calcularTotais();
            return orcamentoRepository.save(orcamento);
        }
        throw new RuntimeException("Orçamento não encontrado");
    }

    public Orcamento aprovarOrcamento(Long orcamentoId) {
        Optional<Orcamento> orcamentoOpt = orcamentoRepository.findById(orcamentoId);
        if (orcamentoOpt.isPresent()) {
            Orcamento orcamento = orcamentoOpt.get();
            orcamento.setAprovado(true);
            return orcamentoRepository.save(orcamento);
        }
        throw new RuntimeException("Orçamento não encontrado");
    }

    public List<Orcamento> listarTodos() {
        return orcamentoRepository.findAll();
    }

    public Optional<Orcamento> buscarPorId(Long id) {
        return orcamentoRepository.findById(id);
    }

    public Optional<Orcamento> buscarPorSolicitacao(Long solicitacaoId) {
        return orcamentoRepository.findBySolicitacaoId(solicitacaoId);
    }
}
