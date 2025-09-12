package com.senai.conta.bancaria.domain.repository;

import com.senai.conta.bancaria.domain.entity.Conta;

import java.util.Optional;

public interface ContaRepository {
    Optional<Conta> findById(Long contaId);
}
