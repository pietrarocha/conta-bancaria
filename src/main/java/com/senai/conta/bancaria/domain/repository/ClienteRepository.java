package com.senai.conta.bancaria.domain.repository;

import com.senai.conta.bancaria.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,String> {

    Optional<Cliente> findByCpfAndAtivoTrue(String cpf);


}
