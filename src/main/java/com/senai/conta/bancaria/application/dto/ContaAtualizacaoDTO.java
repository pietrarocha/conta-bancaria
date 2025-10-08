package com.senai.conta.bancaria.application.dto;

import java.math.BigDecimal;


public record ContaAtualizacaoDTO(
        BigDecimal saldo,
        BigDecimal limite,
        BigDecimal rendimento,
        BigDecimal taxa

) {
}
