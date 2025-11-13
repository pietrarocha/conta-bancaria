package com.senai.conta.bancaria.domain.exceptions;

public class EntidadeNaoEncontradaException extends RuntimeException {
    public EntidadeNaoEncontradaException(String entidade) {
        super(entidade + " não encontrado(a) ou inativo(a)");
    }
}
