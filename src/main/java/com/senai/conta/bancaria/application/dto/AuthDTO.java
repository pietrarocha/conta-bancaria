package com.senai.conta.bancaria.application.dto;

public class AuthDTO {

    public record LoginRequest(
            String email,
            String senha
    ) {}
    public record TokenResponse(
            String token
    ) {}
}