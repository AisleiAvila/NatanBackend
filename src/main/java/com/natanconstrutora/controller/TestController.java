
package com.natanconstrutora.controller;

import com.natanconstrutora.model.Servico;
import com.natanconstrutora.model.Solicitacao;
import com.natanconstrutora.model.User;
import com.natanconstrutora.repository.ServicoRepository;
import com.natanconstrutora.repository.SolicitacaoRepository;
import com.natanconstrutora.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("message", "API Natan Construtora funcionando!");
        status.put("totalUsers", userRepository.count());
        status.put("totalServices", servicoRepository.count());
        status.put("totalRequests", solicitacaoRepository.count());
        return status;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/services")
    public List<Servico> getServices() {
        return servicoRepository.findAll();
    }

    @GetMapping("/requests")
    public List<Solicitacao> getRequests() {
        return solicitacaoRepository.findAll();
    }
}
