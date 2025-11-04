package com.senai.conta.bancaria.domain.entity;

import com.senai.conta.bancaria.domain.enums.TaxaDescricao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "taxa")



public class Taxas {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private TaxaDescricao descricao;

    @Column(nullable = false)
    private Double percentual;

    @Column(nullable = false)
    private Double valorFixo;


}