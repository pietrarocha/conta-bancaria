package com.senai.conta.bancaria.application.dto;

import com.senai.conta.bancaria.domain.entity.DescricaoTaxa;
import com.senai.conta.bancaria.domain.entity.Taxa;
import com.senai.conta.bancaria.domain.entity.TipoPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record TaxaDTO(
        @NotNull
        @Schema(description = "Descricao da taxa", example = "IOF")
        DescricaoTaxa descricao,
        @NotNull
        @Schema(description = "Tipo de pagamentos que usam a taxa", example = "LUZ")
        List<TipoPagamento> tipoPagamento,
        @NotNull
        @Schema(description = "Percentual da taxa", example = "0.1")
        BigDecimal percentual,
        @Schema(description = "Valor fixo da taxa", example = "123")
        BigDecimal valorFixo
) {
    public Taxa toEntity() {
        return Taxa.builder()
                .descricao(this.descricao)
                .tipoPagamento(this.tipoPagamento)
                .percentual(this.percentual)
                .valorFixo(this.valorFixo)
                .build();
    }

    public static TaxaDTO fromEntity(Taxa taxa) {
        return new TaxaDTO(
                taxa.getDescricao(),
                taxa.getTipoPagamento(),
                taxa.getPercentual(),
                taxa.getValorFixo()
        );
    }
}
