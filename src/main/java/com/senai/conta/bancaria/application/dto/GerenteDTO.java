package com.senai.conta.bancaria.application.dto;

import com.senai.conta.bancaria.domain.entity.Gerente;
import com.senai.conta.bancaria.enums.Role;
import lombok.Builder;

@Builder
public record GerenteDTO(
        String id,
        String nome,
        String cpf,
        String email,
        String senha,
        Boolean ativo,
        Role role
) {
    public static GerenteDTO fromEntity(Gerente professor) {
        return GerenteDTO.builder()
                .id(professor.getId())
                .nome(professor.getNome())
                .cpf(professor.getCpf())
                .email(professor.getEmail())
                .ativo(professor.isAtivo())
                .role(professor.getRole())
                .build();
    }

    public Gerente toEntity() {
        return Gerente.builder()
                .id(this.id)
                .nome(this.nome)
                .cpf(this.cpf)
                .email(this.email)
                .senha(this.senha)
                .ativo(this.ativo != null ? this.ativo : true)
                .role(this.role != null ? this.role : Role.GERENTE)
                .build();
    }

}
