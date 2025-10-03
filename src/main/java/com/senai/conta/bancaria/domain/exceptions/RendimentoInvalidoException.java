package com.senai.conta.bancaria.domain.exceptions;

public class RendimentoInvalidoException extends RuntimeException {
    public RendimentoInvalidoException() {
        super("Rendimento deve ser aplicado somente em contas poupança");
    }
}
