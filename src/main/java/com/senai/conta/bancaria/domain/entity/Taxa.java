package com.senai.conta.bancaria.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "taxa", uniqueConstraints = {
        @UniqueConstraint(name = "uk_taxa_descricao", columnNames = "descricao")}
)
public class Taxa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private DescricaoTaxa descricao;

    @Column(precision = 10, scale = 4)
    private BigDecimal percentual;

    @Column(precision = 19, scale = 2)
    private BigDecimal valorFixo;

    @ManyToMany(mappedBy = "taxas")
    private Set<Pagamento> pagamentos;
}
