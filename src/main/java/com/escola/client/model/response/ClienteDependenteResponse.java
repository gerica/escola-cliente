package com.escola.client.model.response;


import com.escola.client.model.entity.Sexo;
import com.escola.client.model.entity.TipoParentesco;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ClienteDependenteResponse(
        Integer id,
        String nome,
        String docCPF,
        Sexo sexo,
        TipoParentesco parentesco,
        String parentescoDescricao,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataNascimento
) {
}
