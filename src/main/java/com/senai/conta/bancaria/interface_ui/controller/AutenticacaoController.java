package com.senai.conta.bancaria.interface_ui.controller;

import com.senai.conta.bancaria.application.service.AutenticacaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticacao", description = "Autenticacao do usuário por meio de IoT")
@RestController
@RequiredArgsConstructor
public class AutenticacaoController {
    private final AutenticacaoService autenticacaoService;

//    @SecurityRequirements
//    @PostMapping
//    @MqttPublisher("banco/autenticaocao/{idCliente}")
//    public MensagemMqttDTO autenticacao(@RequestBody MensagemRestDTO mensagemRestDTO) {
//        return autenticacaoService.autenticacao(mensagemRestDTO);
//    }
//
//    @MqttSubscriber("banco/validacao/{idCliente}")
//    public void validacao(@MqttPayload MensagemMqttDTO mensagemMqttDTO) {
//        autenticacaoService.validacao(mensagemMqttDTO);
//    }
}