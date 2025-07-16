package com.escola.client.model.request;


import com.escola.client.model.entity.StatusContrato;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ContratoRequest(
        @NotNull(message = "O ID do cliente não pode ser nulo.")
        Long idCliente, // O ID do cliente é essencial para associar o contrato

        @NotBlank(message = "O número do contrato é obrigatório.")
        @Size(max = 50, message = "O número do contrato deve ter no máximo 50 caracteres.")
        String numeroContrato,

        @NotNull(message = "A data de início do contrato é obrigatória.")
        @PastOrPresent(message = "A data de início do contrato não pode ser futura.")
        LocalDate dataInicio,

        @FutureOrPresent(message = "A data de fim do contrato não pode ser passada.")
        LocalDate dataFim, // Pode ser nulo para contratos indeterminados

        @NotNull(message = "O valor total do contrato é obrigatório.")
        @DecimalMin(value = "0.01", message = "O valor total do contrato deve ser maior que zero.")
        BigDecimal valorTotal,

        @NotNull(message = "O status do contrato é obrigatório.")
        StatusContrato statusContrato,

        @NotBlank(message = "A descrição do contrato é obrigatória.")
        String descricao,

        String termosCondicoes, // Pode ser opcional

//        @PastOrPresent(message = "A data de assinatura não pode ser futura.")
        LocalDateTime dataAssinatura, // Pode ser opcional

        String periodoPagamento, // Ex: Mensal, Anual

        @FutureOrPresent(message = "A data do próximo pagamento não pode ser passada.")
        LocalDate dataProximoPagamento,

        String observacoes,

        String contratoDoc
) {
}