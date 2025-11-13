package com.senai.conta.bancaria.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record TransferenciaDTO(
        @NotNull
        @NotBlank
        @Size(max = 20)
        @Schema(description = "Numero da conta a receber a transferencia", example = "1234-5")
        String numeroContaDestino,
        @NotNull
        @Schema(description = "Valor a ser transferido", example = "123")
        BigDecimal valor
){
}
