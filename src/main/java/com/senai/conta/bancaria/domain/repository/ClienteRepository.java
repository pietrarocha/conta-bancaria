package com.senai.conta.bancaria.domain.repository;

import com.senai.conta.bancaria.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);

    void deleteByCpf(String cpf);

}
