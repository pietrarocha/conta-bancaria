package com.senai.conta.bancaria.application.dto;


import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ValorSaqueDepositoDTO(
        @NotNull
        BigDecimal valor
) {
}