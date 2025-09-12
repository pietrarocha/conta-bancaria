package com.senai.conta.bancaria.domain.entity;

public class ContaPoupanca extends Conta {
    private double rendimento;
    public ContaPoupanca(String numero, double rendimento) {
        super(numero);
        this.rendimento = rendimento;
    }
    @Override
    public boolean sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            return true;
        }
        return false;
    }
}