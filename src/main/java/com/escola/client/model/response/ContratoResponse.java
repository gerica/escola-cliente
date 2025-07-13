package com.escola.client.model.response;

import java.util.Date;

public record ContratoResponse(
        Long id,
        String nome,
        Date dataNascimento,
        String estado,
        String cidade,
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
