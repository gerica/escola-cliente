package com.escola.client.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_cliente_dependente")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClienteDependente {
    @Id
    @GeneratedValue
    Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "id_cliente", nullable = false)
    Cliente cliente;

    String nome;
    LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", length = 10)
    Sexo sexo;

    @Column(name = "doc_cpf", length = 14, unique = true)
    String docCPF;

    // --- CAMPO NOVO ADICIONADO AQUI ---
    @Enumerated(EnumType.STRING) // Salva o nome do enum (ex: "FILHO") no banco, o que é mais legível
    @Column(name = "parentesco", length = 20, nullable = false)
    TipoParentesco parentesco;

}
