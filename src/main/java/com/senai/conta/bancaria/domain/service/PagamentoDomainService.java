package com.senai.conta.bancaria.domain.service;


import com.senai.conta.bancaria.domain.entity.Pagamento;
import com.senai.conta.bancaria.domain.entity.StatusPagamento;
import com.senai.conta.bancaria.domain.exceptions.SaldoInsuficienteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PagamentoDomainService {
    public Pagamento pagamento(Pagamento pagamento){
        try {
            BigDecimal valorTaxas = BigDecimal.valueOf(0.0);
            BigDecimal valorFixo = BigDecimal.valueOf(0.0);

            pagamento.getTaxas().forEach(taxa -> {
                valorTaxas.add(pagamento.getValorPago().multiply(taxa.getPercentual()));
            });

            pagamento.getTaxas().forEach(taxa -> {
                valorFixo.add(taxa.getValorFixo());
            });

            pagamento.getConta().sacar(valorTaxas.add(valorFixo));
            pagamento.setStatus(StatusPagamento.SUCESSO);
        } catch (SaldoInsuficienteException ex){
            pagamento.setStatus(StatusPagamento.SALDO_INSUFICIENTE);
        } catch (Exception ex){
            pagamento.setStatus(StatusPagamento.FALHA);
        }

        return pagamento;
    }
}
