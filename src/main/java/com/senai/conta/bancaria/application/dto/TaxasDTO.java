package com.senai.conta.bancaria.application.dto;

import com.senai.conta.bancaria.domain.entity.Taxa;
import com.senai.conta.bancaria.domain.entity.Taxas;
import com.senai.conta.bancaria.domain.enums.TaxaDescricao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Schema(
        name = "TaxasDTO",
        description = "DTO para transportar informações de Taxas"
)
@Builder
public record TaxasDTO(
        @Schema(description = "ID da taxa (UUID)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        String id,

        @Schema(description = "Descrição da taxa (enum)", example = "TAXA_MANUTENCAO_CONTA")
        @NotNull(message = "A descrição da taxa não pode ser nula")
        TaxaDescricao descricao,

        @Schema(description = "Percentual aplicado à taxa", example = "2.50")
        @NotNull(message = "O percentual não pode ser nulo")
        @Digits(integer = 3, fraction = 2, message = "O percentual deve ter até 3 dígitos inteiros e 2 decimais")
        Double percentual,

        @Schema(description = "Valor fixo da taxa", example = "20.00")
        @NotNull(message = "O valor fixo não pode ser nulo")
        @Digits(integer = 10, fraction = 2, message = "O valor fixo deve ter até 10 dígitos inteiros e 2 decimais")
        Double valorFixo
) {
    public static TaxasDTO fromEntity(Taxas taxa) {
        return TaxasDTO.builder()
                .id(taxa.getId())
                .descricao(taxa.getDescricao())
                .percentual(taxa.getPercentual())
                .valorFixo(taxa.getValorFixo())
                .build();
    }

    public static Taxas toEntity(TaxasDTO dto) {
        return Taxas.builder()
                .id(dto.id())
                .descricao(dto.descricao())
                .percentual(dto.percentual())
                .valorFixo(dto.valorFixo())
                .build();
    }
}

