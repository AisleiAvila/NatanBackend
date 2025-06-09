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
            User prestador = new User("pedro", "pedro@email.com", passwordEncoder.encode("123456"), "Pedro Santos");
            prestador.setTelefone("913456789");
            prestador.setEndereco("Rua das Oliveiras, 456, Coimbra");
            prestador.setRegiao(Regiao.COIMBRA);
            prestador.setRoles(Set.of(rolePrestador));

            // Admin
            User admin = new User("admin", "admin@natanconstrutora.com", passwordEncoder.encode("admin123"), "Administrador");
            admin.setTelefone("914567890");
            admin.setEndereco("Sede da Empresa");
            admin.setRegiao(Regiao.SAO_MIGUEL);
            admin.setRoles(Set.of(roleAdmin));

            userRepository.saveAll(Arrays.asList(cliente, prestador, admin));
        }

        // Criar serviços se não existirem
        if (servicoRepository.count() == 0) {
            Servico servico1 = new Servico();
            servico1.setNome("Reparação de Torneiras");
            servico1.setDescricao("Conserto e substituição de torneiras domésticas");
            servico1.setPrecoTabelado(new BigDecimal("50.00"));
            servico1.setRegioesAtendimento(Set.of(Regiao.AVEIRO, Regiao.COIMBRA));
            servico1.setAtivo(true);

            Servico servico2 = new Servico();
            servico2.setNome("Instalação Elétrica");
            servico2.setDescricao("Instalação e manutenção de sistemas elétricos");
            servico2.setPrecoTabelado(new BigDecimal("120.00"));
            servico2.setRegioesAtendimento(Set.of(Regiao.SAO_MIGUEL, Regiao.AVEIRO, Regiao.COIMBRA));
            servico2.setAtivo(true);

            servicoRepository.saveAll(Arrays.asList(servico1, servico2));
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