package com.senai.conta.bancaria.domain.exceptions;

public class ContaMesmoTipoException extends RuntimeException {

    public ContaMesmoTipoException() {
        super("O Cliente já possui uma conta do mesmo tipo.");
    }
}
