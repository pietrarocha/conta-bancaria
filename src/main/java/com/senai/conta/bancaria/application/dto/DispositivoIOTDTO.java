package com.senai.conta.bancaria.application.dto;

import com.senai.conta.bancaria.domain.entity.DispositivoIOT;
import com.senai.conta.bancaria.domain.entity.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record DispositivoIOTDTO(
        @NotNull
        @Schema(description = "Codigo serial do dispositivo", example = "123")
        String codigoSerial,
        @NotNull
        @Schema(description = "Chave publica do dispositivo", example = "123")
        String chavePublica,
        @NotNull
        @Schema(description = "Cpf do cliente do dispositivo", example = "12345678910")
        String cpfCliente
) {
    public DispositivoIOT toEntity(Cliente cliente) {
        return DispositivoIOT.builder()
                .codigoSerial(this.codigoSerial)
                .chavePublica(this.chavePublica)
                .cliente(cliente)
                .ativo(true)
                .build();
    }

    public static DispositivoIOTDTO fromEntity(DispositivoIOT dispositivoIoT) {
        return new DispositivoIOTDTO(
                dispositivoIoT.getCodigoSerial(),
                dispositivoIoT.getChavePublica(),
                dispositivoIoT.getCliente().getCpf()
        );
    }
}