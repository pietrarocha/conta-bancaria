package com.senai.conta.bancaria.application.dto;

import com.senai.conta.bancaria.domain.entity.TipoConta;
import com.senai.conta.bancaria.domain.entity.*;
import com.senai.conta.bancaria.domain.exceptions.TipoDeContaInvalidaException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
public record ContaResumoDTO(
        @NotNull
        @NotBlank
        @Schema(description = "Tipo de conta", example = "CONTA_CORRENTE")
        TipoConta tipoConta,
        @NotNull
        @NotBlank
        @Size(max = 20)
        @Schema(description = "Numero da conta", example = "1234-5")
        String numero,
        @NotNull
        @NotBlank
        @Schema(description = "Saldo da conta", example = "123")
        BigDecimal saldo
) {
    public Conta toEntity(Cliente cliente) {
        if(tipoConta == TipoConta.CONTA_CORRENTE){
            return ContaCorrente.builder()
                    .numero(this.numero)
                    .saldo(this.saldo)
                    .ativa(true)
                    .cliente(cliente)
                    .limite(new BigDecimal("500.000"))
                    .taxa(new BigDecimal("0.05"))
                    .build();
        } else if(tipoConta == TipoConta.CONTA_POUPANCA){
            return ContaPoupanca.builder()
                    .numero(this.numero)
                    .saldo(this.saldo)
                    .ativa(true)
                    .cliente(cliente)
                    .rendimento(new BigDecimal("0.01"))
                    .build();
        }
        throw new TipoDeContaInvalidaException();
    }

    public static ContaResumoDTO fromEntity(Conta conta) {
        return new ContaResumoDTO(
                conta.getTipo(),
                conta.getNumero(),
                conta.getSaldo()
        );
    }
}
