package com.natanconstrutora.config;

import com.natanconstrutora.model.*;
import com.natanconstrutora.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Criar roles se não existirem
        if (roleRepository.count() == 0) {
            Role roleCliente = new Role(RoleName.ROLE_CLIENTE);
            Role rolePrestador = new Role(RoleName.ROLE_PRESTADOR);
            Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
            roleRepository.saveAll(Arrays.asList(roleCliente, rolePrestador, roleAdmin));
        }

        // Criar usuários se não existirem
        if (userRepository.count() == 0) {
            Role roleCliente = roleRepository.findByName(RoleName.ROLE_CLIENTE).get();
            Role rolePrestador = roleRepository.findByName(RoleName.ROLE_PRESTADOR).get();
            Role roleAdmin = roleRepository.findByName(RoleName.ROLE_ADMIN).get();

            // Cliente
            User cliente = new User("joao", "joao@email.com", passwordEncoder.encode("123456"), "João Silva");
            cliente.setTelefone("912345678");
            cliente.setEndereco("Rua das Flores, 123, Aveiro");
            cliente.setRegiao(Regiao.AVEIRO);
            cliente.setRoles(Set.of(roleCliente));

            // Prestador
            User prestador = new User("mario", "mario@email.com", passwordEncoder.encode("123456"), "Mário Santos");
            prestador.setTelefone("913456789");
            prestador.setEndereco("Rua dos Serviços, 456, Aveiro");
            prestador.setRegiao(Regiao.AVEIRO);
            prestador.setRoles(Set.of(rolePrestador));

            // Admin
            User admin = new User("admin", "admin@natanconstrutora.com", passwordEncoder.encode("admin123"), "Administrador");
            admin.setRoles(Set.of(roleAdmin));

            userRepository.saveAll(Arrays.asList(cliente, prestador, admin));
        }

        // Criar serviços se não existirem
        if (servicoRepository.count() == 0) {
            Servico servico1 = new Servico("Reparação de Canalizações", 
                                         "Reparação de fugas e problemas de canalização",
                                         new BigDecimal("25.00"));
            servico1.setRegioesAtendimento(Set.of(Regiao.AVEIRO, Regiao.COIMBRA));
            servico1.setAtivo(true);

            Servico servico2 = new Servico("Instalação Elétrica", 
                                         "Instalação e reparação de sistemas elétricos",
                                         new BigDecimal("50.00"));
            servico2.setRegioesAtendimento(Set.of(Regiao.AVEIRO, Regiao.COIMBRA, Regiao.SAO_MIGUEL));
            servico2.setAtivo(true);

            Servico servico3 = new Servico("Instalação de Tomadas", 
                                         "Instalação de novas tomadas elétricas",
                                         new BigDecimal("35.00"));
            servico3.setRegioesAtendimento(Set.of(Regiao.SAO_MIGUEL, Regiao.AVEIRO, Regiao.COIMBRA));
            servico3.setAtivo(true);

            servicoRepository.saveAll(Arrays.asList(servico1, servico2, servico3));
        }

        // Criar solicitações de teste se não existirem
        if (solicitacaoRepository.count() == 0) {
            User cliente = userRepository.findByUsername("joao").get();
            Servico servico = servicoRepository.findAll().get(0);

            Solicitacao solicitacao1 = new Solicitacao(cliente, servico, 
                                                      "A torneira da cozinha está a pingar constantemente",
                                                      "Rua das Flores, 456, Aveiro", 
                                                      Regiao.AVEIRO);

            solicitacaoRepository.save(solicitacao1);
        }
    }
}