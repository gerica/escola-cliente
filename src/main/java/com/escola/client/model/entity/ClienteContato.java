package com.escola.client.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_cliente_contato")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class ClienteContato {
    @Id
    @GeneratedValue
    Integer id;

    String numero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "id_cliente", nullable = false)
    Cliente cliente;

}
