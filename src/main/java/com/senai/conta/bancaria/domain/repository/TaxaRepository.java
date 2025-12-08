package com.senai.conta.bancaria.domain.repository;

import com.senai.conta.bancaria.domain.entity.Taxa;
import com.senai.conta.bancaria.domain.entity.TipoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaxaRepository extends JpaRepository<Taxa, String> {
    @Query("SELECT t FROM Taxa t WHERE :tipoPagamento MEMBER OF t.tipoPagamento")
    List<Taxa> findAllByTipoPagamento(@Param("tipoPagamento") TipoPagamento tipoPagamento);
}