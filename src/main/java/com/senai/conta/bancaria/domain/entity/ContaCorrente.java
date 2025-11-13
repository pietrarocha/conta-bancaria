package com.senai.conta.bancaria.domain.entity;

import com.senai.conta.bancaria.domain.exceptions.SaldoInsuficienteException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("CORRENTE")
public class ContaCorrente extends Conta{
    @Column(precision = 19, scale = 2)
    private BigDecimal limite;

    @Column(precision = 10, scale = 4)
    private BigDecimal taxa;

    @Override
    public TipoConta getTipo() {
        return TipoConta.CONTA_CORRENTE;
    }

    @Override
    public void sacar(BigDecimal valor){
        validarValorMaiorQueZero(valor, "sacar");
        BigDecimal custoSaque = valor.multiply(taxa);
        BigDecimal totalSaque = valor.add(custoSaque);

        if (this.getSaldo().add(this.limite).compareTo(totalSaque) < 0){
            throw new SaldoInsuficienteException();
        }

        this.setSaldo(this.getSaldo().subtract(totalSaque));
    }
}
