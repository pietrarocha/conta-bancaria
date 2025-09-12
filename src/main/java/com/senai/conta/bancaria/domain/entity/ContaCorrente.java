package com.senai.conta.bancaria.domain.entity;

public class ContaCorrente extends Conta {
    private double limite;
    private double taxa;
    public ContaCorrente(String numero, double limite, double taxa) {
        super(numero);
        this.limite = limite;
        this.taxa = taxa;
    }
    @Override
    public boolean sacar(double valor) {
        if (valor > 0 && valor <= (saldo + limite)) {
            saldo -= valor;
            return true;
        }
        return false;
    }
}