package com.senai.conta.bancaria.domain.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Conta> contas = new ArrayList<>();

}