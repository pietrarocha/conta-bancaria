package com.senai.conta.bancaria.domain.exceptions;

public class ValoresNegativosException extends RuntimeException {
    public ValoresNegativosException(String message) {
        super("Não é possível realizar a operação: " + operacao = "com valores negativos!");
    }
}
