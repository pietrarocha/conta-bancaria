package com.senai.conta.bancaria.application.service;

import com.senai.conta.bancaria.application.dto.PagamentoDTO;
import com.senai.conta.bancaria.domain.entity.StatusPagamento;
import com.senai.conta.bancaria.domain.exceptions.BoletoPagoException;
import com.senai.conta.bancaria.domain.exceptions.EntidadeNaoEncontradaException;
import com.senai.conta.bancaria.domain.repository.ContaRepository;
import com.senai.conta.bancaria.domain.repository.PagamentoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    private final ContaRepository contaRepository;
    private final AutenticacaoService autenticacaoService;

    public PagamentoDTO registrarPagamento(PagamentoDTO dto) {
        var conta = contaRepository.findByNumeroAndAtivaTrue(dto.numeroConta())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Conta"));
        if(pagamentoRepository.existsByContaAndBoletoAndStatus(conta, dto.boleto(), StatusPagamento.SUCESSO)){
            throw new BoletoPagoException();
        }

        var pagamento = dto.toEntity(conta);
        pagamento.setStatus(StatusPagamento.PROCESSANDO);
        autenticacaoService.criar(conta.getCliente());

        return PagamentoDTO.fromEntity(pagamentoRepository.save(pagamento));
    }

    @Transactional(readOnly = true)
    public List<PagamentoDTO> listarPagamentos() {
        return pagamentoRepository.findAll()
                .stream()
                .map(PagamentoDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public PagamentoDTO buscarPagamentoPorBoleto(String boleto) {
        return PagamentoDTO.fromEntity(pagamentoRepository.findByBoleto(boleto));
    }
}