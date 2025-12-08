package com.senai.conta.bancaria.interface_ui.controller;

import com.rafaelcosta.spring_mqttx.domain.annotation.MqttPayload;
import com.rafaelcosta.spring_mqttx.domain.annotation.MqttPublisher;
import com.rafaelcosta.spring_mqttx.domain.annotation.MqttSubscriber;
import com.senai.conta.bancaria.application.dto.DispositivoIOTDTO;
import com.senai.conta.bancaria.application.service.DispositivoIOTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "DispositivoIoT", description = "Gerenciamento de dispositivo IoT dos clientes do banco")
@RestController
@RequiredArgsConstructor
public class DispositivoIoTController {
    private final DispositivoIOTService dispositivoIoTService;

    @Operation(
            summary = "Validar com um dispositivo IoT",
            description = "Realiza a validacao pelo dispositivo",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = DispositivoIOTDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                            "codigoSerial": "123",
                                            "chavePublica": "123",
                                            "cpfCliente": "12345678910"
                                         }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dispositivo validado com sucesso"),
            }
    )
    @SecurityRequirements
    @PostMapping
    @MqttPublisher("banco/dispositivoIoT")
    public DispositivoIOTDTO validacao(@RequestBody DispositivoIOTDTO dispositivoIoTDTO) {
        return dispositivoIoTService.validacao(dispositivoIoTDTO);
    }

    @MqttSubscriber("banco/dispositivoIoT")
    public void salvar(@MqttPayload DispositivoIOTDTO dispositivoIoTDTO) {
        dispositivoIoTService.salvar(dispositivoIoTDTO);
    }
}