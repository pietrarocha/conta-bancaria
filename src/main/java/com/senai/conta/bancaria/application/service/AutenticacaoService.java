package com.senai.conta.bancaria.application.service;

import com.senai.conta.bancaria.domain.entity.*;
import com.senai.conta.bancaria.domain.exceptions.DataExpiradaException;
import com.senai.conta.bancaria.domain.exceptions.EntidadeNaoEncontradaException;
import com.senai.conta.bancaria.domain.repository.CodigoAutenticacaoRepository;
import com.senai.conta.bancaria.domain.repository.PagamentoRepository;
import com.senai.conta.bancaria.domain.repository.TaxaRepository;
import com.senai.conta.bancaria.domain.service.PagamentoDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {
    private final CodigoAutenticacaoRepository autenticacaoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final TaxaRepository taxaRepository;
    private final PagamentoDomainService pagamentoDomainService;

    public void criar(Cliente cliente) {
        Autenticacao autenticacao = new Autenticacao();
        autenticacao.setCodigo(UUID.randomUUID().toString());
        autenticacao.setCliente(cliente);
        autenticacao.setExpiraEm(LocalDateTime.now().plusDays(1));
        autenticacao.setValidado(false);

        autenticacaoRepository.save(autenticacao);
    }

    public void validar(Cliente cliente) {
        Autenticacao autenticacao = autenticacaoRepository.findByCliente(cliente);
        if(autenticacao != null){
            if(autenticacao.getExpiraEm().isAfter(LocalDateTime.now())){
                autenticacao.setValidado(true);
                Pagamento pagamento = pagamentoRepository.findByStatus(StatusPagamento.PROCESSANDO).orElseThrow(
                        () -> new EntidadeNaoEncontradaException("Pagamento"));


                List<Taxa> taxas = taxaRepository.findAllByTipoPagamento(pagamento.getTipoPagamento());
                if(taxas.isEmpty()){
                    throw new EntidadeNaoEncontradaException("Taxa");
                }

                pagamentoRepository.save(pagamentoDomainService.pagamento(pagamento, taxas));
                autenticacaoRepository.save(autenticacao);
            } else {
                throw new DataExpiradaException();
            }

        } else {
            throw new EntidadeNaoEncontradaException("Autenticacao");
        }
    }

}