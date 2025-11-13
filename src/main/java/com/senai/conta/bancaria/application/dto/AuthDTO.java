package com.senai.conta.bancaria.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthDTO {

    public record LoginRequest(
            @Schema(description = "Cpf do cliente", example = "12345678910")
            String cpf,
            @Schema(description = "Senha do cliente", example = "senha")
            String senha
    ) {}
    public record TokenResponse(
            String token
    ) {}
}