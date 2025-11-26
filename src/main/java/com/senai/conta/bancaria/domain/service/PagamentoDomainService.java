package com.senai.conta.bancaria.domain.service;

import com.senai.conta.bancaria.domain.entity.Pagamento;
import com.senai.conta.bancaria.domain.entity.Taxa;
import com.senai.conta.bancaria.domain.enums.StatusPagamento;
import com.senai.conta.bancaria.domain.exceptions.SaldoInsuficienteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PagamentoDomainService {
    public Pagamento pagamento(Pagamento pagamento){
        try {
            BigDecimal valorTaxas = BigDecimal.ZERO;
            BigDecimal valorFixo = BigDecimal.ZERO;

            Set<Taxa> taxas = new HashSet<>(pagamento.getTaxas());

            for (Taxa taxa : taxas) {
                valorTaxas = valorTaxas.add(pagamento.getValorPago().multiply(taxa.getPercentual()));
                valorFixo = valorFixo.add(taxa.getValorFixo());
            }

            pagamento.getConta().sacar(valorTaxas.add(valorFixo));
            pagamento.setStatus(StatusPagamento.SUCESSO);
        } catch (SaldoInsuficienteException ex){
            pagamento.setStatus(StatusPagamento.SALDO_INSUFICIENTE);
        } catch (Exception ex){
            pagamento.setStatus(StatusPagamento.FALHA);
        }

        pagamento.setDataPagamento(LocalDateTime.now());
        return pagamento;
    }
}