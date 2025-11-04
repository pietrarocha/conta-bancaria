package com.senai.conta.bancaria.domain.entity;

import com.senai.conta.bancaria.domain.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Data
@SuperBuilder
@Table(name="pagamento")
@DiscriminatorValue("PAGAMENTO")

public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String boleto;


    @Column(nullable = false, unique = true)
    private LocalDateTime dataPagamento;

    @Column(nullable = false)
    private Double valorPago;

    @Column(nullable = false)
    private StatusPagamento status;



    // Associação com a Conta
    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    // Associação ManyToOne com Taxa
    @ManyToOne
    @JoinColumn(name = "taxa_id", nullable = true)
    private Taxas taxa;


    // Métodos de lógica de pagamento podem ser adicionados aqui
}