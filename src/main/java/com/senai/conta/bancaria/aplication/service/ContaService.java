package com.senai.conta.bancaria.aplication.service;

import com.senai.conta.bancaria.domain.entity.Conta;
import org.springframework.stereotype.Service;

@Service
public class ContaService {
    public boolean sacar(Conta conta, double valor) {
        if (conta == null) {
            System.out.println("Conta é nula!");
            return false;
        }
        if (valor <= 0) {
            System.out.println("Valor inválido");
            return false;
        }
        return conta.sacar(valor);
    }

    public boolean transferir(Conta conta, Conta conta1, double valor) {

        return false;
    }}