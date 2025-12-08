package com.senai.conta.bancaria.domain.exceptions;

public class DataExpiradaException extends RuntimeException {
    public DataExpiradaException() {
        super("A data da operação foi expirada");
    }
}