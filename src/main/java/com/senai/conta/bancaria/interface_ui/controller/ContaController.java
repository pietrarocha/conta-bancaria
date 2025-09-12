package com.senai.conta.bancaria.interface_ui.controller;

import com.senai.conta.bancaria.aplication.dto.OperacaoDTO;
import com.senai.conta.bancaria.aplication.dto.TransferenciaDTO;
import com.senai.conta.bancaria.aplication.service.ContaService;
import com.senai.conta.bancaria.domain.entity.Conta;
import com.senai.conta.bancaria.domain.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/contas")
public class ContaController {
    @Autowired
    private ContaService contaService;
    @Autowired
    private ContaRepository contaRepository;
    @PostMapping("/depositar")
    public ResponseEntity<String> depositar(@RequestBody OperacaoDTO dto) {
        Optional<Conta> contaOpt = contaRepository.findById(dto.getContaId());
        if (contaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Conta não encontrada");
        }
        boolean sucesso = contaService.sacar(contaOpt.get(), dto.getValor());
        return sucesso
                ? ResponseEntity.ok("Depósito realizado")
                : ResponseEntity.badRequest().body("Valor inválido (mínimo R$10)");
    }
    @PostMapping("/sacar")
    public ResponseEntity<String> sacar(@RequestBody OperacaoDTO dto) {
        Optional<Conta> contaOpt = contaRepository.findById(dto.getContaId());
        if (contaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Conta não encontrada");
        }
        boolean sucesso = contaService.sacar(contaOpt.get(), dto.getValor());
        return sucesso
                ? ResponseEntity.ok("Saque realizado")
                : ResponseEntity.badRequest().body("Valor inválido ou saldo insuficiente");
    }
    @PostMapping("/transferir")
    public ResponseEntity<String> transferir(@RequestBody TransferenciaDTO dto) {
        Optional<Conta> origemOpt = contaRepository.findById(dto.getContaOrigemId());
        Optional<Conta> destinoOpt = contaRepository.findById(dto.getContaDestinoId());
        if (origemOpt.isEmpty() || destinoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Conta(s) não encontrada(s)");
        }
        boolean sucesso = contaService.transferir(origemOpt.get(), destinoOpt.get(), dto.getValor());
        return sucesso
                ? ResponseEntity.ok("Transferência realizada")
                : ResponseEntity.badRequest().body("Erro na transferência (valor inválido ou saldo insuficiente)");
    }
}