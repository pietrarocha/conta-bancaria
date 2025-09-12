package com.senai.conta.bancaria.domain.entity;

public abstract class Conta {
    protected String numero;
    protected double saldo;
    public Conta(String numero) {
        this.numero = numero;
        this.saldo = 0.0;
    }
    public abstract boolean sacar(double valor);
    public boolean depositar(double valor) {
        if (valor > 10) {
            this.saldo += valor;
            return true;
        }
        return false;
    }
    public boolean transferir(Conta destino, double valor) {
        if (valor > 0 && this.sacar(valor)) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }
    public double getSaldo() {
        return saldo;
    }
}