package com.senai.conta.bancaria.application.service;

import com.senai.conta.bancaria.application.dto.PagamentoDTO;
import com.senai.conta.bancaria.domain.entity.Cliente;
import com.senai.conta.bancaria.domain.entity.StatusPagamento;
import com.senai.conta.bancaria.domain.exceptions.BoletoPagoException;
import com.senai.conta.bancaria.domain.repository.PagamentoRepository;
import com.senai.conta.bancaria.domain.service.PagamentoDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    private final PagamentoDomainService pagamentoDomainService;

    public PagamentoDTO registrarPagamento(PagamentoDTO dto) {
        //TODO: cliente do pagamento
        if(pagamentoRepository.existsByContaAndBoletoAndStatus(dto.contaDTO().toEntity(new Cliente()), dto.boleto(), StatusPagamento.SUCESSO)){
            throw new BoletoPagoException();
        }

        var pagamento = pagamentoDomainService.pagamento(dto.toEntity());

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
