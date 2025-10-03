package com.senai.conta.bancaria.domain.exceptions;

public class TipoDeContaInvalidaException extends RuntimeException {
    public TipoDeContaInvalidaException() {
        super("Tipo de conta inválida. Os tipos aceitos são: 'CORRENTE' ou 'POUPANCA'.");
    }
}
