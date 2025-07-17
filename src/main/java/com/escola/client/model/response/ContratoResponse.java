package com.escola.client.model.response;

import com.escola.client.model.entity.StatusContrato;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ContratoResponse(
        Long idContrato,
        Long idCliente,
        String nomeCliente,
        String numeroContrato,
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