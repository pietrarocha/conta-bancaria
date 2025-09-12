package com.senai.conta.bancaria.aplication.dto;


public class TransferenciaDTO {
    private Long contaOrigemId;
    private Long contaDestinoId;
    private double valor;
    public TransferenciaDTO() {}
    public TransferenciaDTO(Long contaOrigemId, Long contaDestinoId, double valor) {
        this.contaOrigemId = contaOrigemId;
        this.contaDestinoId = contaDestinoId;
        this.valor = valor;
    }
    public Long getContaOrigemId() {
        return contaOrigemId;
    }
    public void setContaOrigemId(Long contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }
    public Long getContaDestinoId() {
        return contaDestinoId;
    }
    public void setContaDestinoId(Long contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
}