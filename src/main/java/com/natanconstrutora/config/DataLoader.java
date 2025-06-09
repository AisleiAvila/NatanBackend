package com.natanconstrutora.config;

import com.natanconstrutora.model.Categoria;
import com.natanconstrutora.model.Cliente;
import com.natanconstrutora.model.Funcionario;
import com.natanconstrutora.model.Material;
import com.natanconstrutora.model.Prestador;
import com.natanconstrutora.model.RegiaoEnum;
import com.natanconstrutora.model.Role;
import com.natanconstrutora.model.RoleName;
import com.natanconstrutora.model.Servico;
import com.natanconstrutora.model.User;
import com.natanconstrutora.repository.CategoriaRepository;
import com.natanconstrutora.repository.ClienteRepository;
import com.natanconstrutora.repository.FuncionarioRepository;
import com.natanconstrutora.repository.MaterialRepository;
import com.natanconstrutora.repository.PrestadorRepository;
import com.natanconstrutora.repository.RoleRepository;
import com.natanconstrutora.repository.ServicoRepository;
import com.natanconstrutora.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PrestadorRepository prestadorRepository;

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
        admin.setEmail("admin@natanconstrutora.com");
        admin.setNome("Administrador");
        admin.setRoles(new HashSet<>(Arrays.asList(adminRole)));
        userRepository.save(admin);

        // Criar clientes
        Cliente cliente1 = new Cliente();
        cliente1.setNome("João Silva");
        cliente1.setEmail("joao@email.com");
        cliente1.setTelefone("(11) 99999-9999");
        cliente1.setNif("123456789");
        cliente1.setRegiao(RegiaoEnum.SUDESTE);
        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Maria Santos");
        cliente2.setEmail("maria@email.com");
        cliente2.setTelefone("(11) 88888-8888");
        cliente2.setNif("987654321");
        cliente2.setRegiao(RegiaoEnum.NORDESTE);
        clienteRepository.save(cliente2);

        Cliente cliente3 = new Cliente();
        cliente3.setNome("Pedro Oliveira");
        cliente3.setEmail("pedro@email.com");
        cliente3.setTelefone("(11) 77777-7777");
        cliente3.setNif("456789123");
        cliente3.setRegiao(RegiaoEnum.SUL);
        clienteRepository.save(cliente3);

        // Criar funcionários
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setNome("Carlos Souza");
        funcionario1.setEmail("carlos@email.com");
        funcionario1.setTelefone("(11) 66666-6666");
        funcionario1.setNif("789123456");
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

        // Criar categoria
        Categoria categoria1 = new Categoria();
        categoria1.setNome("Construção");
        categoria1.setDescricao("Serviços de construção civil");
        categoria1.setAtivo(true);
        categoriaRepository.save(categoria1);

        // Criar prestador
        Prestador prestador1 = new Prestador();
        prestador1.setNome("José Pereira");
        prestador1.setEmail("jose@prestador.com");
        prestador1.setTelefone("(11) 55555-5555");
        prestador1.setNif("123456781");
        prestador1.setDescricao("Especialista em construção civil");
        prestador1.setAvaliacaoMedia(4.5);
        prestador1.setRegiao(RegiaoEnum.SUDESTE);
        prestador1.setDataCadastro(LocalDateTime.now());
        prestadorRepository.save(prestador1);

        // Criar serviços
        Servico servico1 = new Servico();
        servico1.setNome("Alvenaria");
        servico1.setDescricao("Construção de paredes");
        servico1.setPrecoHora(new BigDecimal("100.00"));
        servico1.setDataCriacao(LocalDateTime.now());
        servico1.setStatus("DISPONÍVEL");
        servico1.setCliente(cliente1);  // Utilizando o cliente1 criado anteriormente
        servico1.setCategoria(categoria1); // Adicionando a categoria
        servico1.setPrestador(prestador1); // Adicionando o prestador
        servicoRepository.save(servico1);
    }
}