package com.senai.conta.bancaria.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ValorSaqueDepositoDTO (
        @NotNull
        @Schema(description = "Valor a ser depositado/sacado", example = "123")
        BigDecimal valor
){
}
