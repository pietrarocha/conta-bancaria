package com.senai.conta.bancaria.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Cliente {
    String nome;
    Long cpf;

@OneToMany (mappedBy = "conta")
List<Conta> contas;

}