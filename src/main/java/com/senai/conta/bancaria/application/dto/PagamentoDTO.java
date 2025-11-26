package com.senai.conta.bancaria.application.dto;

import com.senai.conta.bancaria.domain.entity.Conta;
import com.senai.conta.bancaria.domain.entity.Pagamento;
import com.senai.conta.bancaria.domain.entity.Taxa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record PagamentoDTO(
        @Schema(description = "Conta para pagamento", example = "123")
        String numeroConta,
        @NotNull
        @NotBlank
        @Schema(description = "Boleto a ser pago", example = "123")
        String boleto,
        @Schema(description = "Valor pago", example = "123")
        BigDecimal valorPago,
        //TODO: Tipo de pagamento
        @NotNull
        @Schema(description = "Taxas do pagamento")
        List<TaxaDTO> taxas
) {
    public Pagamento toEntity(Conta conta, Set<Taxa> listTaxas) {
        return Pagamento.builder()
                .conta(conta)
                .boleto(this.boleto)
                .valorPago(this.valorPago)
                .taxas(new HashSet<>(listTaxas))
                .build();
    }

    public static PagamentoDTO fromEntity(Pagamento pagamento) {
        return new PagamentoDTO(
                pagamento.getConta().getNumero(),
                pagamento.getBoleto(),
                pagamento.getValorPago(),
                pagamento.getTaxas().stream().map(TaxaDTO::fromEntity).toList()
        );
    }
}