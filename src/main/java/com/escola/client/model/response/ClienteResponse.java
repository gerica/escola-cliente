package com.escola.client.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ClienteResponse(
        Long id,
        String nome,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,
        MunicipioResponse cidade,
        String docCPF,
        String docRG,
        String telResidencial,
        String telCelular,
        String endereco,
        String email,
        String profissao,
        String localTrabalho
) {
}
