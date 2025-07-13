package com.escola.client.model.request;

import java.time.LocalDate;

public record ClienteRequest(
        Integer id,
        String nome,
        LocalDate dataNascimento,
        MunicipioRequest cidade,
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
