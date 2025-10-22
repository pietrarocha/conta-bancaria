package com.senai.conta.bancaria.domain.exceptions;

public class RendimentoInvalidoException extends RuntimeException {
    public RendimentoInvalidoException() {
        super("O rendimento deve ser aplicado somente em conta poupança.");
    }
}