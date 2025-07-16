package com.escola.client.model.request;

import com.escola.client.model.entity.TipoContato;

public record ClienteContatoRequest(
        Integer id,
        Integer idCliente,
        String numero,
        TipoContato tipoContato,
        String observacao
) {
}
