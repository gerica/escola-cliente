package com.escola.client.model.entity;


public enum StatusCliente {
    ATIVO("Ativo"),
    INATIVO("Inativo"),
    BLOQUEADO("Bloqueado"),
    PENDENTE_APROVACAO("Pendente Aprovação");

    private final String descricao;

    StatusCliente(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}