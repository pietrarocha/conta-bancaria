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
    private String codigoSerial;

    @Column(nullable = false)
    private String chavePublica;

    @Column(nullable = false)
    private boolean ativo;

    @OneToOne
    @JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_dispositivo_cliente"))
    private Cliente cliente;
}