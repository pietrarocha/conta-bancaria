package com.senai.conta.bancaria.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("POUPANCA")
public class ContaPoupanca extends Conta{
    @Column(precision = 10, scale = 4)
    private BigDecimal rendimento;

    @Override
    public TipoConta getTipo() {
        return TipoConta.CONTA_POUPANCA;
    }

    public void aplicarRendimento(){
        setSaldo(getSaldo().add(getSaldo().multiply(rendimento)));
    }
}
