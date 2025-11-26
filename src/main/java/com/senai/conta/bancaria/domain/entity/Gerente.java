package com.senai.conta.bancaria.domain.entity;

import com.senai.conta.bancaria.domain.enums.TipoUsuario;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
//@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("ADMIN")
public class Gerente extends Usuario{

    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.ADMIN;
    }
}
