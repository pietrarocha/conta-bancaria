package com.senai.conta.bancaria.application.service;


import com.senai.conta.bancaria.application.dto.ClienteRegistroDTO;
import com.senai.conta.bancaria.application.dto.ClienteResponseDTO;
import com.senai.conta.bancaria.domain.exceptions.EntidadeNaoEncontradaException;
import com.senai.conta.bancaria.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    boolean jaTemTipo = contas.stream()
            .anyMatch(c -> c.getClass().equals(novaConta.getClass()) && c.isAtiva());

    if(jaTemTipo)
        throw new RuntimeException("Cliente já possui uma conta ativa do tipo ");

    cliente.getContas().add(novaConta);

  return ClienteResponseDTO.fromEntity(repository.save(cliente));

  }


    public List<ClienteResponseDTO> listarClientesAtivos() {
    return repository.findAllByAtivoTrue().stream()
            .map(ClienteResponseDTO::fromEntity)
            .toList();
    }

    public ClienteResponseDTO buscarClienteAtivoPorCpf(String cpf) {
    var cliente = repository.findByCpfAndAtivoTrue(cpf) .orElseThrow(
            () -> new EntidadeNaoEncontradaException("Cliente")
    );
    return ClienteResponseDTO.fromEntity(cliente);
    }

    public ClienteResponseDTO atualizarCliente(String cpf, ClienteRegistroDTO dto) {
        var cliente = repository.findByCpfAndAtivoTrue(cpf) .orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cliente")
        );
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());

        return ClienteResponseDTO.fromEntity(repository.save(cliente));

    }

    public void deletarCliente(String cpf) {
        var cliente = repository.findByCpfAndAtivoTrue(cpf) .orElseThrow(
                () -> new EntidadeNaoEncontradaException("Cliente")
        );
        cliente.setAtivo(false);
        cliente.getContas().forEach(
                conta -> conta.setAtiva(false)
        );
        repository.save(cliente);
    }
}
