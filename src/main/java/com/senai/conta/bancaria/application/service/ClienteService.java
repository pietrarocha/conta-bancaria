package com.senai.conta.bancaria.application.service;


import com.senai.conta.bancaria.application.dto.ClienteRegistroDTO;
import com.senai.conta.bancaria.application.dto.ClienteResponseDTO;
import com.senai.conta.bancaria.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

private final ClienteRepository repository;


public ClienteResponseDTO registrarClienteOuAnexarConta(ClienteRegistroDTO dto) {

    var cliente = repository.findByCpfAndAtivoTrue(dto.cpf()).orElseGet(
            () -> repository.save(dto.toEntity())
    );

    var contas = cliente.getContas();
    var novaConta = dto.contaDTO().toEntity(cliente);


    return null;

  }

}
