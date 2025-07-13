package com.escola.client.model.request;

import java.util.Date;

public record ContratoRequest(
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
