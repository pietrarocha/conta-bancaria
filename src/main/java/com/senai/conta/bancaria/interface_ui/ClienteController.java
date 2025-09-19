package com.senai.conta.bancaria.interface_ui;


import com.senai.conta.bancaria.application.dto.ClienteRegistroDTO;
import com.senai.conta.bancaria.application.dto.ClienteResponseDTO;
import com.senai.conta.bancaria.application.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {


    private final ClienteService service;


    @PostMapping
   public ClienteResponseDTO registrarCliente(@RequestBody ClienteRegistroDTO dto) {
        return service.registrarClienteOuAnexarConta(dto);


    }
}
