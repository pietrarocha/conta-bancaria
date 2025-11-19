package com.senai.conta.bancaria.domain.repository;

import com.senai.conta.bancaria.domain.entity.DescricaoTaxa;
import com.senai.conta.bancaria.domain.entity.Taxa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxaRepository extends JpaRepository<Taxa, String> {
    Optional<Taxa> findByDescricao(DescricaoTaxa descricao);
}
