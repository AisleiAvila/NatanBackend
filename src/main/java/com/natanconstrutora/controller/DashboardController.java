
package com.natanconstrutora.controller;

import com.natanconstrutora.model.StatusSolicitacao;
import com.natanconstrutora.model.Regiao;
import com.natanconstrutora.repository.SolicitacaoRepository;
import com.natanconstrutora.repository.UserRepository;
import com.natanconstrutora.repository.ServicoRepository;
import com.natanconstrutora.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @GetMapping("/metricas")
    public Map<String, Object> getMetricas() {
        Map<String, Object> metricas = new HashMap<>();
        
        // Contadores gerais
        metricas.put("totalSolicitacoes", solicitacaoRepository.count());
        metricas.put("totalUsuarios", userRepository.count());
        metricas.put("totalServicos", servicoRepository.count());
        metricas.put("totalAvaliacoes", avaliacaoRepository.count());
        
        // Solicitações por status
        Map<String, Long> solicitacoesPorStatus = new HashMap<>();
        for (StatusSolicitacao status : StatusSolicitacao.values()) {
            solicitacoesPorStatus.put(status.name(), solicitacaoRepository.countByStatus(status));
        }
        metricas.put("solicitacoesPorStatus", solicitacoesPorStatus);
        
        // Solicitações por região
        Map<String, Long> solicitacoesPorRegiao = new HashMap<>();
        for (Regiao regiao : Regiao.values()) {
            solicitacoesPorRegiao.put(regiao.name(), 
                (long) solicitacaoRepository.findByRegiao(regiao).size());
        }
        metricas.put("solicitacoesPorRegiao", solicitacoesPorRegiao);
        
        return metricas;
    }
}
