package com.escola.client.model.request;

import java.io.Serializable;

public record MunicipioRequest(String codigo,
                               String descricao,
                               String uf) implements Serializable {
}

