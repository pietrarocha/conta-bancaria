package com.senai.conta.bancaria.application.service;


import com.senai.conta.bancaria.application.dto.ContaAtualizacaoDTO;
import com.senai.conta.bancaria.application.dto.ContaResumoDTO;
import com.senai.conta.bancaria.domain.entity.ContaCorrente;
import com.senai.conta.bancaria.domain.entity.ContaPoupanca;
import com.senai.conta.bancaria.domain.repository.ContaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContaService {
    private final ContaRepository repository;

    @Transactional(readOnly = true)
    public List<ContaResumoDTO> listarTodasContas() {
        return repository.findAllByAtivaTrue().stream()
                .map(ContaResumoDTO::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public ContaResumoDTO buscarContaPorNumero(String numero) {
        return ContaResumoDTO.fromEntity(
                repository.findByNumeroAndAtivaTrue(numero)
                        .orElseThrow(() -> new RuntimeException("Conta não encontrada"))
        );
    }
    public  ContaResumoDTO atualizarConta(String numero, ContaAtualizacaoDTO dto) {

        var conta = repository.findByNumeroAndAtivaTrue(numeroConta).orElseThrow((
        ) -> new RuntimeException("Conta não encontrada")
        );
        conta.setSaldo(dto.saldo());

        if (conta instanceof ContaPoupanca poupanca) {
           poupanca.setRendimento(dto.rendimento());
        }else if (conta instanceof ContaCorrente corrente) {
            corrente.setLimite(dto.limite());
            corrente.setTaxa(dto.taxa());
        }

        return ContaResumoDTO.fromEntity(repository.save(conta));
    }





}