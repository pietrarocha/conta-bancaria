package com.senai.conta.bancaria.domain.repository;

import com.senai.conta.bancaria.domain.entity.Autenticacao;
import com.senai.conta.bancaria.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutenticacaoRepository extends JpaRepository<Autenticacao, String> {
    Autenticacao findByCliente(Cliente cliente);
}