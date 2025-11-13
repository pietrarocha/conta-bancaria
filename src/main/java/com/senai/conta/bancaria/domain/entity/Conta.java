package com.senai.conta.bancaria.domain.entity;

import com.senai.conta.bancaria.domain.exceptions.SaldoInsuficienteException;
import com.senai.conta.bancaria.domain.exceptions.TransferenciaParaMesmaContaException;
import com.senai.conta.bancaria.domain.exceptions.ValoresNegativosException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_conta", discriminatorType = DiscriminatorType.STRING, length = 20)
@Table(name = "conta", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cliente_numero", columnNames = "numero"),
        @UniqueConstraint(name = "uk_cliente_tipo", columnNames = {"cliente_id", "tipo_conta"})} //cliente nao pode ter duas contas de tipos iguais
)
public abstract class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(nullable = false, precision = 19, scale = 2) //precision = tamanho, scale = casas decimais
    private BigDecimal saldo;

    @Column(nullable = false)
    private boolean ativa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_conta_cliente"))
    private Cliente cliente;

    public abstract TipoConta getTipo();

    public void sacar(BigDecimal valor){
        validarValorMaiorQueZero(valor, "sacar");
        if (this.saldo.compareTo(valor) < 0){
            throw new SaldoInsuficienteException();
        }
        this.saldo = this.saldo.subtract(valor);
    }

    public void depositar(BigDecimal valor){
        validarValorMaiorQueZero(valor, "depositar");
        this.saldo = this.saldo.add(valor);
    }

    public void transferir(Conta contaDestino, BigDecimal valor){
        if (this.id.equals(contaDestino.getId())){
            throw new TransferenciaParaMesmaContaException();
        }

        this.sacar(valor);
        contaDestino.depositar(valor);
    }

    protected static void validarValorMaiorQueZero(BigDecimal valor, String operacao) {
        if (valor.compareTo(BigDecimal.ZERO) < 0){
            throw new ValoresNegativosException(operacao);
        }
    }
}
