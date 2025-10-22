package com.senai.conta.bancaria.domain.repository;

import com.senai.conta.bancaria.domain.entity.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GerenteRepository extends JpaRepository<Gerente, String> {
    Optional<Gerente> findByEmail(String email);
}