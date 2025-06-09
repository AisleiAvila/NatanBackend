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

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Override
    public void run(String... args) {
        // Criar roles
        Role adminRole = new Role();
        adminRole.setName(RoleName.ROLE_ADMIN);
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName(RoleName.ROLE_USER);
        roleRepository.save(userRole);

        // Criar usuário admin
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRoles(new HashSet<>(Arrays.asList(adminRole)));
        userRepository.save(admin);

        // Criar clientes
        Cliente cliente1 = new Cliente();
        cliente1.setNome("João Silva");
        cliente1.setEmail("joao@email.com");
        cliente1.setTelefone("(11) 99999-9999");
        cliente1.setCpf("123.456.789-00");
        cliente1.setRegiao(RegiaoEnum.SUDESTE);
        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Maria Santos");
        cliente2.setEmail("maria@email.com");
        cliente2.setTelefone("(11) 88888-8888");
        cliente2.setCpf("987.654.321-00");
        cliente2.setRegiao(RegiaoEnum.NORDESTE);
        clienteRepository.save(cliente2);

        Cliente cliente3 = new Cliente();
        cliente3.setNome("Pedro Oliveira");
        cliente3.setEmail("pedro@email.com");
        cliente3.setTelefone("(11) 77777-7777");
        cliente3.setCpf("456.789.123-00");
        cliente3.setRegiao(RegiaoEnum.SUL);
        clienteRepository.save(cliente3);

        // Criar funcionários
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setNome("Carlos Souza");
        funcionario1.setEmail("carlos@email.com");
        funcionario1.setTelefone("(11) 66666-6666");
        funcionario1.setCpf("789.123.456-00");
        funcionario1.setCargo("Pedreiro");
        funcionario1.setSalario(new BigDecimal("2500.00"));
        funcionarioRepository.save(funcionario1);

        // Criar materiais
        Material material1 = new Material();
        material1.setNome("Cimento");
        material1.setDescricao("Cimento Portland");
        material1.setPrecoUnitario(new BigDecimal("25.00"));
        material1.setQuantidadeEstoque(100);
        materialRepository.save(material1);

        Material material2 = new Material();
        material2.setNome("Areia");
        material2.setDescricao("Areia média");
        material2.setPrecoUnitario(new BigDecimal("50.00"));
        material2.setQuantidadeEstoque(200);
        materialRepository.save(material2);

        // Criar serviços
        Servico servico1 = new Servico();
        servico1.setNome("Alvenaria");
        servico1.setDescricao("Construção de paredes");
        servico1.setPrecoHora(new BigDecimal("100.00"));
        servicoRepository.save(servico1);
    }
}