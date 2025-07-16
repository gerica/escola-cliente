package com.escola.client.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data // Gera getters, setters, toString, equals e hashCode
@NoArgsConstructor // Gera construtor sem argumentos
@AllArgsConstructor // Gera construtor com todos os argumentos
@Entity // Marca a classe como uma entidade JPA
@Table(name = "tb_contrato") // Mapeia a classe para a tabela "tb_contrato" no banco de dados
@FieldDefaults(level = AccessLevel.PRIVATE) // Define que todos os campos são privados por padrão
@Builder
public class Contrato {

    @Id // Marca o campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estratégia de geração de valor para a chave primária (auto-incremento)
    Long idContrato; // Usamos Long para IDs, que é uma boa prática

    // Chave estrangeira para a entidade Cliente
    @ManyToOne // Indica um relacionamento muitos para um (muitos contratos para um cliente)
    @JoinColumn(name = "id_cliente", nullable = false)
    Cliente cliente; // Referência à entidade Cliente

    @Column(name = "numero_contrato", unique = true, nullable = false, length = 50)
    String numeroContrato;

    @Column(name = "data_inicio", nullable = false)
    LocalDate dataInicio; // Usamos LocalDate para representar apenas a data

    @Column(name = "data_fim")
    LocalDate dataFim;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    BigDecimal valorTotal; // BigDecimal é ideal para valores monetários

    @Enumerated(EnumType.STRING) // Define como o enum será persistido no banco de dados
    @Column(name = "status_contrato", nullable = false, length = 50)
    StatusContrato statusContrato; // O tipo agora é o enum StatusContrato

    @Column(name = "descricao", columnDefinition = "TEXT") // Mapeia para um tipo TEXT no banco de dados
    String descricao;

    @Column(name = "termos_condicoes", columnDefinition = "TEXT")
    String termosCondicoes;

    @Column(name = "data_assinatura")
    LocalDateTime dataAssinatura; // Usamos LocalDateTime para data e hora da assinatura

    @Column(name = "periodo_pagamento", length = 50)
    String periodoPagamento;

    @Column(name = "data_proximo_pagamento")
    LocalDate dataProximoPagamento;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    String observacoes;

    @Column(name = "contrato_documento", columnDefinition = "TEXT")
    String contratoDoc;

}