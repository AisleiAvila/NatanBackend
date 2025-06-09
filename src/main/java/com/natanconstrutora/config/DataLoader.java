
package com.natanconstrutora.config;

import com.natanconstrutora.model.*;
import com.natanconstrutora.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
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
        // Criar roles
        if (roleRepository.count() == 0) {
            Role clientRole = new Role(RoleName.ROLE_CLIENT);
            Role prestadorRole = new Role(RoleName.ROLE_PRESTADOR);
            Role adminRole = new Role(RoleName.ROLE_ADMIN);

            roleRepository.saveAll(Arrays.asList(clientRole, prestadorRole, adminRole));
        }

        // Criar usuários
        if (userRepository.count() == 0) {
            // Admin
            User admin = new User("admin", "admin@natanconstrutora.pt", 
                                passwordEncoder.encode("admin123"), "Administrador");
            admin.setTelefone("123456789");
            admin.setEndereco("Rua Principal, 123");
            admin.setRegiao(Regiao.COIMBRA);
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(roleRepository.findByName(RoleName.ROLE_ADMIN).get());
            admin.setRoles(adminRoles);

            // Cliente
            User cliente = new User("joao", "joao@email.pt", 
                                  passwordEncoder.encode("123456"), "João Silva");
            cliente.setTelefone("987654321");
            cliente.setEndereco("Rua das Flores, 456");
            cliente.setRegiao(Regiao.AVEIRO);
            Set<Role> clientRoles = new HashSet<>();
            clientRoles.add(roleRepository.findByName(RoleName.ROLE_CLIENT).get());
            cliente.setRoles(clientRoles);

            // Prestador
            User prestador = new User("carlos", "carlos@email.pt", 
                                    passwordEncoder.encode("123456"), "Carlos Santos");
            prestador.setTelefone("555666777");
            prestador.setEndereco("Rua dos Técnicos, 789");
            prestador.setRegiao(Regiao.AVEIRO);
            Set<Role> prestadorRoles = new HashSet<>();
            prestadorRoles.add(roleRepository.findByName(RoleName.ROLE_PRESTADOR).get());
            prestador.setRoles(prestadorRoles);

            userRepository.saveAll(Arrays.asList(admin, cliente, prestador));
        }

        // Criar serviços
        if (servicoRepository.count() == 0) {
            Servico servico1 = new Servico("Reparação de Torneira", 
                                         "Reparação e substituição de torneiras com defeito",
                                         new BigDecimal("25.00"));
            servico1.setRegioes(Set.of(Regiao.AVEIRO, Regiao.COIMBRA));
            servico1.setAtivo(true);

            Servico servico2 = new Servico("Instalação Elétrica", 
                                         "Instalação e reparação de sistemas elétricos",
                                         new BigDecimal("50.00"));
            servico2.setRegioes(Set.of(Regiao.AVEIRO, Regiao.COIMBRA, Regiao.SAO_MIGUEL));
            servico2.setAtivo(true);

            Servico servico3 = new Servico("Pintura de Parede", 
                                         "Pintura interior e exterior de paredes",
                                         new BigDecimal("35.00"));
            servico3.setRegioes(Set.of(Regiao.COIMBRA));
            servico3.setAtivo(true);

            servicoRepository.saveAll(Arrays.asList(servico1, servico2, servico3));
        }

        // Criar solicitações
        if (solicitacaoRepository.count() == 0) {
            User cliente = userRepository.findByUsername("joao").get();
            Servico servico = servicoRepository.findAll().get(0);

            Solicitacao solicitacao1 = new Solicitacao(cliente, servico, 
                                                      "A torneira da cozinha está a pingar constantemente",
                                                      "Rua das Flores, 456, Aveiro", 
                                                      Regiao.AVEIRO);

            Solicitacao solicitacao2 = new Solicitacao(cliente, 
                                                      servicoRepository.findAll().get(1),
                                                      "Preciso de instalar uma nova tomada no quarto",
                                                      "Rua das Flores, 456, Aveiro", 
                                                      Regiao.AVEIRO);
            solicitacao2.setStatus(StatusSolicitacao.EM_ANDAMENTO);
            solicitacao2.setPrestador(userRepository.findByUsername("carlos").get());

            solicitacaoRepository.saveAll(Arrays.asList(solicitacao1, solicitacao2));
        }

        System.out.println("=== DADOS DE TESTE CARREGADOS ===");
        System.out.println("Admin: admin / admin123");
        System.out.println("Cliente: joao / 123456");
        System.out.println("Prestador: carlos / 123456");
        System.out.println("H2 Console: http://localhost:5000/h2-console");
        System.out.println("JDBC URL: jdbc:h2:mem:natandb");
        System.out.println("Username: sa");
        System.out.println("Password: password");
    }
}
