package com.senai.conta.bancaria.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;


@Entity
@DiscriminatorValue("CORRENTE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class ContaCorrente extends Conta {
    @Column (precision = 19, scale= 2)
    private BigDecimal limite;
    @Column (precision = 19, scale= 4)
    private BigDecimal taxa;

    @Override
    public String getTipo() {
        return "CORRENTE";
    }

    @Override
    public void sacar(BigDecimal valor) {
        validarValorMaiorQueZero(valor);
       if (valor.compareTo(BigDecimal.ZERO)<0)
           throw new IllegalArgumentException("Valor de saque deve ser maior que zero");

       BigDecimal custoSaque = valor.multiply(taxa);
       BigDecimal totalSaque = valor.add(custoSaque);


      if (this.getSaldo().add(this.limite).compareTo(valor) < 0)
           throw new IllegalArgumentException("Saldo insuficiente para saque");

       setSaldo(getSaldo().subtract(totalSaque));
    }
}
