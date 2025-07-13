package com.escola.client.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_cliente")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class Cliente {
    @Id
    @GeneratedValue
    Integer id;
    String nome;
    LocalDate dataNascimento;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "codigo", column = @Column(name = "cidade_codigo")),
            @AttributeOverride(name = "descricao", column = @Column(name = "cidade_descricao")),
            @AttributeOverride(name = "uf", column = @Column(name = "cidade_uf"))
    })
    Municipio cidade;

    @Column(name = "doc_cpf", length = 14, unique = true)
    String docCPF;
    String docRG;
    String endereco;
    String email;
    String profissao;
    String localTrabalho;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = {CascadeType.ALL})
    @JsonIgnore
    @ToString.Exclude
    List<ClienteContato> contatos;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = {CascadeType.ALL})
    @JsonIgnore
    @ToString.Exclude
    List<ClienteDependente> dependentes;
}
