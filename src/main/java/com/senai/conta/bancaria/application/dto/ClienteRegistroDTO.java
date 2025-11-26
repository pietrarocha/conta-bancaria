package com.senai.conta.bancaria.application.dto;

import com.senai.conta.bancaria.domain.entity.Cliente;
import com.senai.conta.bancaria.domain.entity.Conta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;

public record ClienteRegistroDTO(
        @NotNull
        @NotBlank
        @Size(max = 120)
        @Schema(description = "Nome do cliente", example = "Nome")
        String nome,
        @NotNull
        @NotBlank
        @Size(min = 11, max = 11)
        @Schema(description = "Cpf do cliente", example = "87923187533")
        String cpf,
        @NotNull
        @NotBlank
        @Schema(description = "Senha do cliente", example = "senha")
        String senha,
        @NotNull
        @Schema(description = "Conta do cliente")
        ContaResumoDTO contaDTO
) {
        public Cliente toEntity() {
                return Cliente.builder()
                        .nome(this.nome)
                        .cpf(this.cpf)
                        .senha(this.senha)
                        .contas(new ArrayList<Conta>())
                        .ativo(true)
                        .build();
        }
}