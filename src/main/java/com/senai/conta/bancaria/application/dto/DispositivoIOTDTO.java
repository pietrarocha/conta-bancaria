package com.senai.conta.bancaria.application.dto;

import com.senai.conta.bancaria.domain.entity.Cliente;
import com.senai.conta.bancaria.domain.entity.DispositivoIOT;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record DispositivoIOTDTO(
        @NotNull
        @Schema(description = "Descricao da taxa", example = "IOF")
        String codigoSerial,
        @NotNull
        @Schema(description = "Tipo de pagamentos que usam a taxa", example = "LUZ")
        String chavePublica,
        @NotNull
        @Schema(description = "Percentual da taxa", example = "0.1")
        Cliente cliente
) {
    public DispositivoIOT toEntity() {
        return DispositivoIOT.builder()
                .codigoSerial(this.codigoSerial)
                .chavePublica(this.chavePublica)
                .cliente(this.cliente)
                .ativo(true)
                .build();
    }

    public static DispositivoIOTDTO fromEntity(DispositivoIOT dispositivoIoT) {
        return new DispositivoIOTDTO(
                dispositivoIoT.getCodigoSerial(),
                dispositivoIoT.getChavePublica(),
                dispositivoIoT.getCliente()
        );
    }
}