package com.senai.conta.bancaria.domain.exceptions;
public class TransferenciaParaMesmaContaException extends RuntimeException {
    public TransferenciaParaMesmaContaException() {
        super("Não é possível transferir para a mesma conta.");
    }
}