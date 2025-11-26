package com.senai.conta.bancaria.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "dispositivoIoT", uniqueConstraints = {
        @UniqueConstraint(name = "uk_taxa_codigoSerial", columnNames = "codigoSerial")}
)
public class DispositivoIOT {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String codigoSerial;

    @Column(nullable = false)
    private String chavePublica;

    @Column(nullable = false)
    private boolean ativo;

    @OneToOne
    private Cliente cliente;
}