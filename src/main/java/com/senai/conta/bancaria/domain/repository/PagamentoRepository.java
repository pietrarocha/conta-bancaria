package com.senai.conta.bancaria.domain.repository;

import com.senai.conta.bancaria.domain.entity.Conta;
import com.senai.conta.bancaria.domain.entity.Pagamento;
import com.senai.conta.bancaria.domain.entity.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, String> {
    boolean existsByContaAndBoletoAndStatus(Conta conta, String boleto, StatusPagamento status);
    Pagamento findByBoleto(String boleto);
    Optional<Pagamento> findByStatus(StatusPagamento statusPagamento);
}