package com.senai.conta.bancaria.domain.repository;

import com.senai.conta.bancaria.domain.entity.Taxa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxaRepository extends JpaRepository<Taxa, String> {

}
