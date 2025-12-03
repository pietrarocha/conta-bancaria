package com.senai.conta.bancaria.domain.repository;

import com.senai.conta.bancaria.domain.entity.DispositivoIOT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispositivoIOTRepository extends JpaRepository<DispositivoIOT, String> {
}