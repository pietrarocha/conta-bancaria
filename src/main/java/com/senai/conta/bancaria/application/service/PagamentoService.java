package com.senai.conta.bancaria.application.service;

import com.senai.conta.bancaria.application.dto.PagamentoDTO;
import com.senai.conta.bancaria.domain.entity.Taxa;
import com.senai.conta.bancaria.domain.enums.StatusPagamento;
import com.senai.conta.bancaria.domain.exceptions.BoletoPagoException;
import com.senai.conta.bancaria.domain.exceptions.EntidadeNaoEncontradaException;
import com.senai.conta.bancaria.domain.repository.ContaRepository;
import com.senai.conta.bancaria.domain.repository.PagamentoRepository;
import com.senai.conta.bancaria.domain.repository.TaxaRepository;
import com.senai.conta.bancaria.domain.service.PagamentoDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    private final ContaRepository contaRepository;
    private final TaxaRepository taxaRepository;
    private final PagamentoDomainService pagamentoDomainService;

    public PagamentoDTO registrarPagamento(PagamentoDTO dto) {
        var conta = contaRepository.findByNumeroAndAtivaTrue(dto.numeroConta())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Conta"));
        if(pagamentoRepository.existsByContaAndBoletoAndStatus(conta, dto.boleto(), StatusPagamento.SUCESSO)){
            throw new BoletoPagoException();
        }

        Set<Taxa> taxas = dto.taxas().stream()
                .map(t -> taxaRepository.findByDescricao(t.descricao())
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("Taxa")))
                .collect(Collectors.toSet());

        var pagamentoEntity = dto.toEntity(conta, taxas);
        pagamentoEntity.setTaxas(new HashSet<>(pagamentoEntity.getTaxas()));
        var pagamento = pagamentoDomainService.pagamento(pagamentoEntity);

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