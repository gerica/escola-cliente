package com.escola.client.config;

import com.escola.client.model.entity.*;
import com.escola.client.repository.ClienteRepository;
import com.escola.client.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DataInitializer implements CommandLineRunner {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    ClienteRepository clienteRepository;
    Random random = new Random();
    // Lista de nomes de exemplo
    private final String[] FIRST_NAMES = {
            "Ana", "João", "Maria", "Pedro", "Carla", "Lucas", "Mariana", "Gabriel", "Isabela", "Rafael",
            "Laura", "Daniel", "Sofia", "Mateus", "Lívia", "Bruno", "Beatriz", "Guilherme", "Clara", "Felipe"
    };
    private final String[] LAST_NAMES = {
            "Silva", "Santos", "Oliveira", "Souza", "Lima", "Costa", "Pereira", "Rodrigues", "Almeida", "Nascimento",
            "Ferreira", "Gomes", "Martins", "Ribeiro", "Fernandes", "Carvalho", "Araujo", "Melo", "Barbosa", "Dias"
    };

    @Override
    public void run(String... args) {
        log.info("Iniciando a verificação de dados iniciais...");

        criarClientes();

        log.info("Verificação de dados iniciais concluída.");
    }

    void criarClientes() {
        // Verifica se a tabela de Cliente está vazia
        if (clienteRepository.count() == 0) {
            log.info("Nenhum cliente encontrado. Iniciando a criação de 25 clientes de teste...");

            List<Cliente> clientes = new ArrayList<>();

            // Gera 25 clientes de teste
            for (int i = 1; i <= 25; i++) {
                // Cria o objeto Cliente
                Cliente cliente = Cliente.builder()
                        .nome(generateRandomName())
                        .dataNascimento(LocalDate.of(1980 + (i % 20), (i % 12) + 1, (i % 28) + 1))
                        .cidade(getMunicipio(i))
                        .docCPF(String.format("%03d.%03d.%03d-%02d", random.nextInt(999), random.nextInt(999), random.nextInt(999), random.nextInt(99)))
                        .docRG(String.format("RG%07d", random.nextInt(10000000)))
                        .endereco("Rua das Flores, " + (100 + i))
                        .email("cliente" + i + "@example.com")
                        .profissao(getProfessionForIndex(i))
                        .localTrabalho("Empresa ABC " + (i % 3 + 1))
                        .build();

                // --- NOVO: Cria e associa contatos e dependentes ---
                List<ClienteContato> contatos = criarContatosParaCliente(cliente, i);
                List<ClienteDependente> dependentes = criarDependentesParaCliente(cliente);

                cliente.setContatos(contatos);
                cliente.setDependentes(dependentes);
                // ----------------------------------------------------

                clientes.add(cliente);
            }

            // Salva todos os clientes (e seus contatos/dependentes em cascata)
            clienteRepository.saveAll(clientes);
            log.info(">>> 25 clientes de teste criados com sucesso.");
        } else {
            log.info("Clientes já existem no banco de dados. Nenhuma ação necessária.");
        }
    }

    /**
     * NOVO: Cria uma lista de contatos para um cliente.
     * Gera 2 contatos: um e-mail e um celular.
     */
    private List<ClienteContato> criarContatosParaCliente(Cliente cliente, int index) {
        // Contato 1: E-mail
        ClienteContato contatoEmail = ClienteContato.builder()
                .numero(String.format("(%02d) 9%04d-%04d", 11 + (index % 10), random.nextInt(10000), random.nextInt(10000)))
                .cliente(cliente) // Associa ao cliente pai
                .build();

        // Contato 2: Celular
        ClienteContato contatoCelular = ClienteContato.builder()
//                .tipo("CELULAR")
                .numero(String.format("(%02d) 9%04d-%04d", 11 + (index % 10), random.nextInt(10000), random.nextInt(10000)))
                .cliente(cliente) // Associa ao cliente pai
                .build();

        return new ArrayList<>(List.of(contatoEmail, contatoCelular));
    }

    /**
     * NOVO: Cria uma lista de dependentes para um cliente.
     * Gera 1 ou 2 dependentes com parentescos variados.
     */
    private List<ClienteDependente> criarDependentesParaCliente(Cliente cliente) {
        List<ClienteDependente> dependentes = new ArrayList<>();
        TipoParentesco[] todosOsParentescos = TipoParentesco.values();

        // --- Dependente 1 ---
        String nomeDependente1 = generateRandomName(); // Geramos o nome primeiro para usá-lo na lógica do sexo
        Sexo sexoDependente1 = determinarSexoPeloNome(nomeDependente1);

        ClienteDependente dependente1 = ClienteDependente.builder()
                .nome(nomeDependente1)
                .dataNascimento(cliente.getDataNascimento().plusYears(20 + random.nextInt(10)))
                .parentesco(todosOsParentescos[random.nextInt(todosOsParentescos.length)]) // Pega um parentesco aleatório
                .sexo(sexoDependente1) // Adiciona o sexo determinado pelo nome
                .cliente(cliente)
                .docCPF(String.format("%03d.%03d.%03d-%02d", random.nextInt(999), random.nextInt(999), random.nextInt(999), random.nextInt(99)))
                .build();
        dependentes.add(dependente1);

        // --- Adiciona um segundo dependente (opcional) ---
        if (random.nextBoolean()) {
            String nomeDependente2 = generateRandomName();
            Sexo sexoDependente2 = determinarSexoPeloNome(nomeDependente2);

            ClienteDependente dependente2 = ClienteDependente.builder()
                    .nome(nomeDependente2)
                    .dataNascimento(cliente.getDataNascimento().plusYears(random.nextInt(5)).minusYears(random.nextInt(5)))
                    .parentesco(todosOsParentescos[random.nextInt(todosOsParentescos.length)]) // Pega um parentesco aleatório
                    .sexo(sexoDependente2) // Adiciona o sexo determinado pelo nome
                    .cliente(cliente)
                    .docCPF(String.format("%03d.%03d.%03d-%02d", random.nextInt(999), random.nextInt(999), random.nextInt(999), random.nextInt(99)))
                    .build();
            dependentes.add(dependente2);
        }

        return dependentes;
    }

    /**
     * Determina o sexo com base na terminação do primeiro nome.
     * Nomes terminados em 'a' são considerados do sexo feminino.
     *
     * @param nomeCompleto O nome completo gerado.
     * @return O enum Sexo (MULHER ou HOMEN).
     */
    private Sexo determinarSexoPeloNome(String nomeCompleto) {
        // Pega apenas o primeiro nome para a verificação
        String primeiroNome = nomeCompleto.split(" ")[0];

        if (primeiroNome.endsWith("a")) {
            return Sexo.MULHER;
        }
        return Sexo.HOMEM;
    }

    // Helper method to assign different UFs
    private Municipio getMunicipio(int index) {
        switch (index % 5) {
            case 0:
                return Municipio.builder()
                        .codigo("9825")
                        .descricao("São Paulo")
                        .uf("SP")
                        .build();
            case 1:
                return Municipio.builder()
                        .codigo("10500")
                        .descricao("Rio de Janeiro")
                        .uf("RJ")
                        .build();
            case 2:
                return Municipio.builder()
                        .codigo("6889")
                        .descricao("Belo Horizonte")
                        .uf("MG")
                        .build();
            case 3:
                return Municipio.builder()
                        .codigo("8359")
                        .descricao("Feira de Santana") // Corrigido nome da cidade
                        .uf("BA")
                        .build();
            case 4:
                return Municipio.builder()
                        .codigo("6281")
                        .descricao("Curitiba")
                        .uf("PR")
                        .build();
            default:
                return Municipio.builder()
                        .codigo("5550")
                        .descricao("Brasília")
                        .uf("DF")
                        .build();
        }
    }

    // Helper method to assign different professions
    private String getProfessionForIndex(int index) {
        switch (index % 5) {
            case 0:
                return "Engenheiro";
            case 1:
                return "Médico";
            case 2:
                return "Professor";
            case 3:
                return "Desenvolvedor";
            case 4:
                return "Designer";
            default:
                return "Outros"; // Should not happen
        }
    }

    private String generateRandomName() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }
}