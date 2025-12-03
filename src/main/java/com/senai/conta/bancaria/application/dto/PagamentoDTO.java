package com.senai.conta.bancaria.application.dto;

import com.senai.conta.bancaria.domain.entity.Conta;
import com.senai.conta.bancaria.domain.entity.Pagamento;
import com.senai.conta.bancaria.domain.entity.TipoPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PagamentoDTO(
        @Schema(description = "Conta para pagamento", example = "123")
        String numeroConta,
        @NotNull
        @NotBlank
        @Schema(description = "Boleto a ser pago", example = "123")
        String boleto,
        @NotNull
        @Schema(description = "Tipo de pagamento", example = "LUZ")
        TipoPagamento tipoPagamento,
        @Schema(description = "Valor pago", example = "123")
        BigDecimal valorPago
) {
    public Pagamento toEntity(Conta conta) {
        return Pagamento.builder()
                .conta(conta)
                .boleto(this.boleto)
                .tipoPagamento(this.tipoPagamento)
                .valorPago(this.valorPago)
                .build();
    }

    public static PagamentoDTO fromEntity(Pagamento pagamento) {
        return new PagamentoDTO(
                pagamento.getConta().getNumero(),
                pagamento.getBoleto(),
                pagamento.getTipoPagamento(),
                pagamento.getValorPago()
        );
    }
}