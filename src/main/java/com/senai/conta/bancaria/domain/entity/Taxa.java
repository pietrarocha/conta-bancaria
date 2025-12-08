package com.senai.conta.bancaria.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Taxa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private DescricaoTaxa descricao;

    @ElementCollection(targetClass = TipoPagamento.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "taxa_tipo_pagamento", joinColumns = @JoinColumn(name = "taxa_id"))
    @Column(name = "tipo_pagamento")
    private List<TipoPagamento> tipoPagamento;

    @Column(precision = 10, scale = 4)
    private BigDecimal percentual;

    @Column(precision = 19, scale = 2)
    private BigDecimal valorFixo;
}