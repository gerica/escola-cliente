package com.escola.client.model.response;

import com.escola.client.model.entity.StatusContrato;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ContratoResponse(
        Long idContrato,
        String numeroContrato,
        Long idCliente, // ID do cliente para referência, em vez do objeto Cliente completo
        String nomeCliente, // Nome do cliente para facilitar a exibição
        LocalDate dataInicio,
        LocalDate dataFim,
        BigDecimal valorTotal,
        StatusContrato statusContrato,
        String descricao,
        String termosCondicoes,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime dataAssinatura,
        String periodoPagamento,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataProximoPagamento,
        String observacoes,
        String contratoDoc
) {
}