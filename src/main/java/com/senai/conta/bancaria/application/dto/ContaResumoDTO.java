package com.senai.conta.bancaria.application.dto;

import com.senai.conta.bancaria.domain.entity.Cliente;
import com.senai.conta.bancaria.domain.entity.Conta;
import com.senai.conta.bancaria.domain.entity.ContaCorrente;
import com.senai.conta.bancaria.domain.entity.ContaPoupanca;

import java.math.BigDecimal;

public record ContaResumoDTO(

        String numero,

        String tipo,

        BigDecimal saldo
) {
    public Conta toEntity(Cliente cliente){
        if("CORRENTE".equalsIgnoreCase(tipo)){
            return ContaCorrente.builder()
                    .numero(this.numero)
                    .saldo(this.saldo)
                    .ativa(true)
                    .cliente(cliente)
                    .limite(new BigDecimal("500.00"))
                    .taxa(new BigDecimal("0.05"))
                    .build();
        } else if ("POUPANCA".equalsIgnoreCase(tipo)){
            return ContaPoupanca.builder()
                    .numero(this.numero)
                    .saldo(this.saldo)
                    .ativa(true)
                    .rendimento(new BigDecimal("0.01"))
                    .cliente(cliente)
                    .build();
        }

        return null;
    }

    public static ContaResumoDTO fromEntity(Conta conta){
        return new ContaResumoDTO(
                conta.getNumero(),
                conta.getTipo(),
                conta.getSaldo()
        );
    }

}
