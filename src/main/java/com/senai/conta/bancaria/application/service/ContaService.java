package com.senai.conta.bancaria.application.service;


import com.senai.conta.bancaria.application.dto.ContaAtualizacaoDTO;
import com.senai.conta.bancaria.application.dto.ContaResumoDTO;
import com.senai.conta.bancaria.application.dto.TransferenciaDTO;
import com.senai.conta.bancaria.application.dto.ValorSaqueDepositoDTO;
import com.senai.conta.bancaria.domain.entity.Conta;
import com.senai.conta.bancaria.domain.entity.ContaCorrente;
import com.senai.conta.bancaria.domain.entity.ContaPoupanca;
import com.senai.conta.bancaria.domain.exceptions.EntidadeNaoEncontradaException;
import com.senai.conta.bancaria.domain.exceptions.RendimentoInvalidoException;
import com.senai.conta.bancaria.domain.repository.ContaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("Conta"))
        );
    }
    public ContaResumoDTO atualizarConta(String numeroDaConta, ContaAtualizacaoDTO dto){
        var conta = buscarContaAtivaPorNumero(numeroDaConta);

        if (conta instanceof ContaPoupanca poupanca){
            poupanca.setRendimento(dto.rendimento());
        } else if (conta instanceof ContaCorrente corrente) {
            corrente.setLimite(dto.limite());
            corrente.setTaxa(dto.taxa());
        }

        conta.setSaldo(dto.saldo());
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }

    public void deletarConta(String numeroDaConta) {
        var conta = buscarContaAtivaPorNumero(numeroDaConta);
        conta.setAtiva(false);
        repository.save(conta);
    }

    private Conta buscarContaAtivaPorNumero(String numeroDaConta) {
        var conta = repository.findByNumeroAndAtivaTrue(numeroDaConta).orElseThrow(
                () -> new EntidadeNaoEncontradaException("Conta")
        );
        return conta;
    }

    public ContaResumoDTO sacar(String numeroDaConta, ValorSaqueDepositoDTO dto) {
        var conta = buscarContaAtivaPorNumero(numeroDaConta);
        conta.sacar(dto.valor());
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }

    public ContaResumoDTO depositar(String numeroDaConta, ValorSaqueDepositoDTO dto) {
        var conta = buscarContaAtivaPorNumero(numeroDaConta);
        conta.depositar(dto.valor());
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }
    public ContaResumoDTO transferir(String numeroDaConta, TransferenciaDTO dto){
        Conta contaOrigem = buscarContaAtivaPorNumero(numeroDaConta);
        Conta contaDestino = buscarContaAtivaPorNumero(dto.contaDestino());


        var ContaOrigem= buscarContaAtivaPorNumero(numeroDaConta);
        var ContaDestino= buscarContaAtivaPorNumero(dto.contaDestino());

        contaOrigem.transferir(dto.valor(), contaDestino);

        repository.save(contaDestino);
        return ContaResumoDTO.fromEntity(repository.save(contaOrigem));

    }

    public ContaResumoDTO aplicarRendimento(String numeroConta) {
        var conta = buscarContaAtivaPorNumero(numeroConta);
        if (conta instanceof ContaPoupanca poupanca){
            poupanca.aplicarRendimento();
            return ContaResumoDTO.fromEntity(repository.save(conta));
        }
            throw new RendimentoInvalidoException();
        }
    }
