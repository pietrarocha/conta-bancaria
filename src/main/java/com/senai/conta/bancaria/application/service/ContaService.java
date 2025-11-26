package com.senai.conta.bancaria.application.service;


import com.senai.conta.bancaria.application.dto.ContaAtualizacaoDTO;
import com.senai.conta.bancaria.application.dto.ContaResumoDTO;
import com.senai.conta.bancaria.application.dto.TransferenciaDTO;
import com.senai.conta.bancaria.application.dto.ValorSaqueDepositoDTO;
import com.senai.conta.bancaria.domain.entity.Conta;
import com.senai.conta.bancaria.domain.entity.ContaCorrente;
import com.senai.conta.bancaria.domain.entity.ContaPoupanca;
import com.senai.conta.bancaria.domain.enums.TipoConta;
import com.senai.conta.bancaria.domain.exceptions.EntidadeNaoEncontradaException;
import com.senai.conta.bancaria.domain.exceptions.RendimentoInvalidoException;
import com.senai.conta.bancaria.domain.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;

    @Transactional(readOnly = true)
    public List<ContaResumoDTO> listarConta() {
        return contaRepository.findAllByAtivaTrue()
                .stream()
                .map(ContaResumoDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContaResumoDTO buscarContaPorNumero(String numero) {
        var conta = buscarContaPorNumeroEAtivoTrue(numero);
        return ContaResumoDTO.fromEntity(conta);
    }

    public ContaResumoDTO atualizarConta(String numero, ContaAtualizacaoDTO dto) {
        var conta = buscarContaPorNumeroEAtivoTrue(numero);

        conta.setSaldo(dto.saldo());

        if (conta instanceof ContaCorrente contaCorrente &&
                dto.tipoConta() == TipoConta.CONTA_CORRENTE) {
            contaCorrente.setLimite(dto.limite());
            contaCorrente.setTaxa(dto.taxa());
        } else if (conta instanceof ContaPoupanca contaPoupanca &&
                dto.tipoConta() == TipoConta.CONTA_POUPANCA) {
            contaPoupanca.setRendimento(dto.rendimento());
        }

        return ContaResumoDTO.fromEntity(contaRepository.save(conta));
    }

    public ContaResumoDTO sacar(String numero, ValorSaqueDepositoDTO dto){
        Conta conta = buscarContaPorNumeroEAtivoTrue(numero);
        conta.sacar(dto.valor());
        return ContaResumoDTO.fromEntity(contaRepository.save(conta));
    }

    public ContaResumoDTO depositar(String numero, ValorSaqueDepositoDTO dto){
        var conta = buscarContaPorNumeroEAtivoTrue(numero);
        conta.depositar(dto.valor());
        return ContaResumoDTO.fromEntity(contaRepository.save(conta));
    }

    public ContaResumoDTO transferir(String numero, TransferenciaDTO dto){
        var conta = buscarContaPorNumeroEAtivoTrue(numero);
        var contaDestino = buscarContaPorNumeroEAtivoTrue(dto.numeroContaDestino());
        conta.transferir(contaDestino, dto.valor());
        contaRepository.save(contaDestino);
        return ContaResumoDTO.fromEntity(contaRepository.save(conta));
    }

    public ContaResumoDTO aplicarRendimento(String numero){
        var conta = buscarContaPorNumeroEAtivoTrue(numero);
        if (conta instanceof ContaPoupanca contaPoupanca){
            contaPoupanca.aplicarRendimento();
            return ContaResumoDTO.fromEntity(contaRepository.save(conta));
        }
        throw new RendimentoInvalidoException();
    }

    public void desativarConta(String numero) {
        var conta = buscarContaPorNumeroEAtivoTrue(numero);
        conta.setAtiva(false);
        contaRepository.save(conta);
    }

    private Conta buscarContaPorNumeroEAtivoTrue(String numero){
        return contaRepository.findByNumeroAndAtivaTrue(numero).orElseThrow(
                () -> new EntidadeNaoEncontradaException("Conta"));
    }
}
