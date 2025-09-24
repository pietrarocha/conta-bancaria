package com.senai.conta.bancaria.interface_ui;


import com.senai.conta.bancaria.application.dto.ClienteRegistroDTO;
import com.senai.conta.bancaria.application.dto.ClienteResponseDTO;
import com.senai.conta.bancaria.application.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {


    private final ClienteService service;


    @PostMapping
   public ResponseEntity<ClienteResponseDTO> registrarCliente(@RequestBody ClienteRegistroDTO dto) {
        ClienteResponseDTO novoCliente = service.registrarClienteOuAnexarConta(dto);
        return ResponseEntity.created(

                URI.create("/api/cliente/cpf/" + novoCliente.cpf())
        ).body(novoCliente);
    }
    @GetMapping
    public ResponseEntity<ClienteResponseDTO> listarClientesAtivos(){
        return ResponseEntity.ok(service.listarClientesAtivos());
    }
}
