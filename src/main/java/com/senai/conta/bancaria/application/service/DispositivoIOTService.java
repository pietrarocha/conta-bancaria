package com.senai.conta.bancaria.application.service;

import com.senai.conta.bancaria.application.dto.DispositivoIOTDTO;
import com.senai.conta.bancaria.domain.entity.Cliente;
import com.senai.conta.bancaria.domain.entity.DispositivoIOT;
import com.senai.conta.bancaria.domain.exceptions.EntidadeNaoEncontradaException;
import com.senai.conta.bancaria.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DispositivoIOTService {
    private final AutenticacaoService autenticacaoService;
    private final ClienteRepository clienteRepository;

    public DispositivoIOTDTO validacao(DispositivoIOTDTO dto) {
        Cliente cLiente = clienteRepository.findByCpfAndAtivoTrue(dto.cpfCliente()).
                orElseThrow(() -> new EntidadeNaoEncontradaException("Conta"));

        DispositivoIOT dispositivoIoT = dto.toEntity(cLiente);

        dispositivoIoT.setCliente(cLiente);

        return DispositivoIOTDTO.fromEntity(dispositivoIoT);
    }

    public void salvar(DispositivoIOTDTO dto) {
        Cliente cLiente = clienteRepository.findByCpfAndAtivoTrue(dto.cpfCliente()).
                orElseThrow(() -> new EntidadeNaoEncontradaException("Conta"));

        autenticacaoService.validar(cLiente);
    }

}