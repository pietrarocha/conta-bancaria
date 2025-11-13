package com.senai.conta.bancaria.domain.exceptions;

public class ContaMesmoTipoException extends RuntimeException {
    public ContaMesmoTipoException() {
        super("Cliente já possui uma conta ativa deste tipo");
    }
}
