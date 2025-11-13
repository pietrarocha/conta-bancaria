package com.senai.conta.bancaria.domain.service;


import com.senai.conta.bancaria.domain.entity.Pagamento;
import com.senai.conta.bancaria.domain.entity.StatusPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagamentoDomainService {
    public Pagamento pagamento(Pagamento pagamento){
        //TODO: taxa do pagamento
        pagamento.getConta().sacar(pagamento.getValorPago());
        //TODO: status de falha
        pagamento.setStatus(StatusPagamento.SUCESSO);
        return pagamento;
    }
}
