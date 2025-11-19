package com.senai.conta.bancaria.application.dto;

import com.senai.conta.bancaria.domain.entity.Conta;
import com.senai.conta.bancaria.domain.entity.Pagamento;
import com.senai.conta.bancaria.domain.entity.Taxa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

public record PagamentoDTO(
        @Schema(description = "Conta para pagamento", example = "123")
        String numeroConta,
        @NotNull
        @NotBlank
        @Schema(description = "Boleto a ser pago", example = "123")
        String boleto,
        @Schema(description = "Valor pago", example = "123")
        BigDecimal valorPago,
        @NotNull
        @Schema(description = "Taxas do pagamento")
        List<TaxaDTO> taxas
) {
    public Pagamento toEntity(Conta conta, HashSet<Taxa> listTaxas) {
        return Pagamento.builder()
                .conta(conta)
                .boleto(this.boleto)
                .valorPago(this.valorPago)
                .taxas(listTaxas)
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
