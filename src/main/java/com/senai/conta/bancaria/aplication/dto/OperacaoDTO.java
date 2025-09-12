package com.senai.conta.bancaria.aplication.dto;

public class OperacaoDTO {
    private Long contaId;
    private double valor;
    public OperacaoDTO() {}
    public OperacaoDTO(Long contaId, double valor) {
        this.contaId = contaId;
        this.valor = valor;
    }
    public Long getContaId() {
        return contaId;
    }
    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
}