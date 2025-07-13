package com.escola.client.model.request;

public record ClienteContatoRequest(
        Integer id,
        Integer idCliente,
        String numero) {
}
