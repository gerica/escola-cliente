package com.escola.client.model.request;

import com.escola.client.model.entity.Sexo;
import com.escola.client.model.entity.TipoParentesco;

import java.time.LocalDate;

public record ClienteDependenteRequest(
        Integer id,
        Integer idCliente,
        String nome,
        String docCPF,
        Sexo sexo,
        LocalDate dataNascimento,
        TipoParentesco parentesco
) {
}
