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
        @Schema(description = "Conta para pagamento", example = "Nome")
        ContaResumoDTO contaDTO,
        @NotNull
        @NotBlank
        @Size(min = 11, max = 11)
        @Schema(description = "Boleto a ser pago", example = "12345678910")
        String boleto,
        @Schema(description = "Valor pago", example = "123")
        BigDecimal valorPago,
//        @Schema(description = "Data do pagamento")
//        LocalDateTime dataPagamento,
//        @NotNull
//        @NotBlank
//        @Schema(description = "Status do pagamento", example = "SUCESSO")
//        StatusPagamento status,
        @NotNull
        @Schema(description = "Taxas do pagamento")
        List<TaxaDTO> taxas
) {
    public Pagamento toEntity() {
        List<Taxa> listTaxas = this.taxas.stream().map(TaxaDTO::toEntity).toList();
        Conta conta = this.toEntity().getConta();
        return Pagamento.builder()
                .conta(conta)
                .boleto(this.boleto)
                .valorPago(this.valorPago)
                .dataPagamento(LocalDateTime.now())
                .taxas(new HashSet<>(listTaxas))
                .build();
    }

    public static PagamentoDTO fromEntity(Pagamento pagamento) {
        return new PagamentoDTO(
                ContaResumoDTO.fromEntity(pagamento.getConta()),
                pagamento.getBoleto(),
                pagamento.getValorPago(),
//                    pagamento.getDataPagamento(),
//                    pagamento.getStatus(),
                pagamento.getTaxas().stream().map(TaxaDTO::fromEntity).toList()
        );
    }
}
