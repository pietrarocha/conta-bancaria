package com.senai.conta.bancaria.application.dto;

import com.senai.conta.bancaria.domain.entity.DescricaoTaxa;
import com.senai.conta.bancaria.domain.entity.Taxa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TaxaDTO(
        @NotNull
        @NotBlank
        @Schema(description = "Descricao da taxa", example = "IOF")
        DescricaoTaxa descricao,
        @NotNull
        @NotBlank
        @Schema(description = "Percentual da taxa", example = "0.1")
        BigDecimal percentual,
        @NotNull
        @NotBlank
        @Schema(description = "Valor fixo da taxa", example = "123")
        BigDecimal valorFixo
) {
    public Taxa toEntity() {
        return Taxa.builder()
                .descricao(this.descricao)
                .percentual(this.percentual)
                .valorFixo(this.valorFixo)
                .build();
    }

    public static TaxaDTO fromEntity(Taxa taxa) {
        return new TaxaDTO(
                taxa.getDescricao(),
                taxa.getPercentual(),
                taxa.getValorFixo()
        );
    }
}
