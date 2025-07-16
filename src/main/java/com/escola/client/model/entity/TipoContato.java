package com.escola.client.model.entity;

public enum TipoContato {
    CELULAR("Celular"),
    RESIDENCIAL("Residencial"),
    COMERCIAL("Comercial"),
    WHATSAPP("WhatsApp"),
    TELEGRAM("Telegram"),
    OUTRO("Outro");

    private final String descricao;

    TipoContato(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}