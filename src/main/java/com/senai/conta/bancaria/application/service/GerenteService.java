package com.senai.conta.bancaria.application.service;

import com.senai.conta.bancaria.application.dto.GerenteDTO;
import com.senai.conta.bancaria.domain.entity.Gerente;
import com.senai.conta.bancaria.domain.repository.GerenteRepository;
import com.senai.conta.bancaria.domain.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GerenteService {

    private final GerenteRepository gerenteRepository;

    private final PasswordEncoder encoder;

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public List<GerenteDTO> listarTodosGerentes() {
        return gerenteRepository.findAll().stream()
                .map(GerenteDTO::fromEntity)
                .toList();
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    public GerenteDTO cadastrarGerente(GerenteDTO dto) {
        Gerente entity = dto.toEntity();
        entity.setSenha(encoder.encode(dto.senha()));
        entity.setRole(Role.CLIENTE);
        gerenteRepository.save(entity);
        return GerenteDTO.fromEntity(entity);
    }
}