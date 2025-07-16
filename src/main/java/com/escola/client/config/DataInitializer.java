package com.escola.client.config;

import com.escola.client.model.entity.*;
import com.escola.client.repository.ClienteRepository;
import com.escola.client.repository.ContratoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DataInitializer implements CommandLineRunner {

    ClienteRepository clienteRepository;
    ContratoRepository contratoRepository; // Injetar o repositório de Contrato
    Random random = new Random();

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

        criarClientesComDependentesEContatos(); // Método único para clientes, dependentes e contatos
        criarContratos(); // Novo método para contratos

        log.info("Verificação de dados iniciais concluída.");
    }

    void criarClientesComDependentesEContatos() {
        if (clienteRepository.count() == 0) {
            log.info("Nenhum cliente encontrado. Iniciando a criação de 25 clientes de teste...");

            List<Cliente> clientes = new ArrayList<>();

            for (int i = 1; i <= 25; i++) {
                Municipio municipio = getMunicipio(i);
                // Cria o objeto Cliente
                Cliente cliente = Cliente.builder()
                        .nome(generateRandomName())
                        .dataNascimento(LocalDate.of(1980 + (i % 20), (i % 12) + 1, (i % 28) + 1))
                        .cidade(municipio.getDescricao())
                        .uf(municipio.getUf())
                        .codigoCidade(municipio.getCodigo())
                        .docCPF(String.format("%03d.%03d.%03d-%02d", random.nextInt(999), random.nextInt(999), random.nextInt(999), random.nextInt(99)))
                        .docRG(String.format("RG%07d", random.nextInt(10000000)))
                        .endereco("Rua das Flores, " + (100 + i))
                        .email("cliente" + i + "@example.com")
                        .profissao(getProfessionForIndex(i))
                        .localTrabalho("Empresa ABC " + (i % 3 + 1))
                        .statusCliente(i % 3 == 0 ? StatusCliente.INATIVO : StatusCliente.ATIVO) // Exemplo de status
                        .build();

                List<ClienteContato> contatos = criarContatosParaCliente(cliente, i);
                List<ClienteDependente> dependentes = criarDependentesParaCliente(cliente);

                cliente.setContatos(contatos);
                cliente.setDependentes(dependentes);

                clientes.add(cliente);
            }

            clienteRepository.saveAll(clientes);
            log.info(">>> 25 clientes de teste (com contatos e dependentes) criados com sucesso.");
        } else {
            log.info("Clientes já existem no banco de dados. Nenhuma ação necessária para clientes, contatos e dependentes.");
        }
    }

    // --- NOVO MÉTODO: CRIAR CONTRATOS ---
    void criarContratos() {
        if (contratoRepository.count() == 0) {
            log.info("Nenhum contrato encontrado. Iniciando a criação de 25 contratos de teste...");

            List<Cliente> clientes = (List<Cliente>) clienteRepository.findAll(); // Pega todos os clientes existentes

            if (clientes.isEmpty()) {
                log.warn("Nenhum cliente encontrado para associar contratos. Crie clientes primeiro.");
                return;
            }

            List<Contrato> contratos = new ArrayList<>();
            StatusContrato[] statusContratoValues = StatusContrato.values();

            for (int i = 0; i < 25; i++) {
                // Pega um cliente aleatório para associar o contrato
                Cliente clienteAleatorio = clientes.get(random.nextInt(clientes.size()));

                LocalDate dataInicio = LocalDate.now().minusDays(random.nextInt(365));
                LocalDate dataFim = dataInicio.plusMonths(random.nextInt(24) + 6); // Contratos de 6 a 30 meses

                Contrato contrato = Contrato.builder()
                        .cliente(clienteAleatorio)
                        .numeroContrato("CONTRATO-" + String.format("%05d", i + 1))
                        .dataInicio(dataInicio)
                        .dataFim(dataFim)
                        .valorTotal(BigDecimal.valueOf(1000 + random.nextDouble() * 9000).setScale(2, RoundingMode.HALF_UP))
                        .statusContrato(statusContratoValues[random.nextInt(statusContratoValues.length)])
                        .descricao("Contrato de prestação de serviços " + (i + 1))
                        .termosCondicoes("Termos e condições padrão do contrato " + (i + 1) + ".")
                        .dataAssinatura(LocalDateTime.now().minusDays(random.nextInt(30)))
                        .periodoPagamento(getPeriodoPagamentoAleatorio())
                        .dataProximoPagamento(LocalDate.now().plusDays(random.nextInt(30)))
                        .observacoes("Observações diversas para o contrato " + (i + 1) + ".")
                        .build();

                contratos.add(contrato);
            }

            contratoRepository.saveAll(contratos);
            log.info(">>> 25 contratos de teste criados com sucesso e associados a clientes.");
        } else {
            log.info("Contratos já existem no banco de dados. Nenhuma ação necessária para contratos.");
        }
    }

    // Helper para gerar período de pagamento aleatório
    private String getPeriodoPagamentoAleatorio() {
        String[] periodos = {"Mensal", "Trimestral", "Semestral", "Anual"};
        return periodos[random.nextInt(periodos.length)];
    }
    // --- FIM DO NOVO MÉTODO ---


    private List<ClienteContato> criarContatosParaCliente(Cliente cliente, int index) {
        List<ClienteContato> contatos = new ArrayList<>();
        TipoContato[] tiposContato = TipoContato.values();

        // Contato 1: Celular
        ClienteContato celular = ClienteContato.builder()
                .numero(String.format("(%02d) 9%04d-%04d", 11 + (index % 10), random.nextInt(10000), random.nextInt(10000)))
                .tipoContato(TipoContato.CELULAR)
                .observacao("Contato principal")
                .cliente(cliente)
                .build();
        contatos.add(celular);

        // Contato 2: Residencial (opcional)
        if (random.nextBoolean()) {
            ClienteContato residencial = ClienteContato.builder()
                    .numero(String.format("(%02d) %04d-%04d", 11 + (index % 10), random.nextInt(10000), random.nextInt(10000)))
                    .tipoContato(TipoContato.RESIDENCIAL)
                    .observacao("Telefone fixo de casa")
                    .cliente(cliente)
                    .build();
            contatos.add(residencial);
        }

        return contatos;
    }

    private List<ClienteDependente> criarDependentesParaCliente(Cliente cliente) {
        List<ClienteDependente> dependentes = new ArrayList<>();
        TipoParentesco[] todosOsParentescos = TipoParentesco.values();

        // Dependente 1
        String nomeDependente1 = generateRandomName();
        Sexo sexoDependente1 = determinarSexoPeloNome(nomeDependente1);

        ClienteDependente dependente1 = ClienteDependente.builder()
                .nome(nomeDependente1)
                .dataNascimento(cliente.getDataNascimento().plusYears(18 + random.nextInt(15))) // Idade mais realista para dependentes
                .parentesco(todosOsParentescos[random.nextInt(todosOsParentescos.length)])
                .sexo(sexoDependente1)
                .docCPF(String.format("%03d.%03d.%03d-%02d", random.nextInt(999), random.nextInt(999), random.nextInt(999), random.nextInt(99)))
                .cliente(cliente)
                .build();
        dependentes.add(dependente1);

        // Adiciona um segundo dependente (opcional)
        if (random.nextBoolean()) {
            String nomeDependente2 = generateRandomName();
            Sexo sexoDependente2 = determinarSexoPeloNome(nomeDependente2);

            ClienteDependente dependente2 = ClienteDependente.builder()
                    .nome(nomeDependente2)
                    .dataNascimento(cliente.getDataNascimento().plusYears(random.nextInt(18)).minusYears(random.nextInt(5))) // Idade mais realista
                    .parentesco(todosOsParentescos[random.nextInt(todosOsParentescos.length)])
                    .sexo(sexoDependente2)
                    .docCPF(String.format("%03d.%03d.%03d-%02d", random.nextInt(999), random.nextInt(999), random.nextInt(999), random.nextInt(99)))
                    .cliente(cliente)
                    .build();
            dependentes.add(dependente2);
        }

        return dependentes;
    }

    private Sexo determinarSexoPeloNome(String nomeCompleto) {
        String primeiroNome = nomeCompleto.split(" ")[0];
        if (primeiroNome.endsWith("a") || primeiroNome.endsWith("A")) { // Considera maiúsculas e minúsculas
            return Sexo.MULHER;
        }
        return Sexo.HOMEM;
    }

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
                        .descricao("Feira de Santana")
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
                return "Outros";
        }
    }

    private String generateRandomName() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }
}