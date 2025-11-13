package com.senai.conta.bancaria.interface_ui.controller;


import com.senai.conta.bancaria.application.dto.ClienteRegistroDTO;
import com.senai.conta.bancaria.application.dto.ClienteResponseDTO;
import com.senai.conta.bancaria.application.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Cliente", description = "Gerenciamento de clientes do banco")
@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @Operation(
            summary = "Cadastrar um novo usuário e sua conta poupança/corrente",
            description = "Realiza o cadastro do cliente e sua conta",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                             "nome": "Nome",
                                             "cpf": 12345678910,
                                             "senha": "senha",
                                             "contaDTO": {
                                                 "tipoConta": "CONTA_POUPANCA",
                                                 "numero": 1234,
                                                 "saldo": 1000
                                             }
                                         }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Cliente não encontrado", value = "\"Cliente não encontrado(a) ou inativo(a)\""),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Mesmo tipo de conta",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Mesmo tipo de conta", value = "\"Cliente já possui uma conta ativa deste tipo\""),
                                    }
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrarCliente(@Valid  @org.springframework.web.bind.annotation.RequestBody ClienteRegistroDTO dto) {
        ClienteResponseDTO novoCliente = clienteService.registrarClienteOuAnexarConta(dto);
        return ResponseEntity.created(
                URI.create("/api/cliente/cpf/" + novoCliente.cpf())
        ).body(novoCliente);
    }

    @Operation(
            summary = "Listar todos os clientes ativos",
            description = "Retorna todos os clientes cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos() {
        return ResponseEntity.ok(clienteService.listarClientesAtivos());
    }

    @Operation(
            summary = "Buscar um cliente ativo pelo cpf",
            description = "Retorna um cliente cadastrado",
            parameters = {
                    @Parameter(name = "cpf", description = "cpf do cliente a ser buscado", example = "12345678910")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente retornado com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(name = "Cliente não encontrado", value = "\"Cliente com id 111111111 não encontrado.\"")
                            )
                    )
            }
    )
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscarClienteAtivoPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(clienteService.buscarClienteAtivoPorCpf(cpf));
    }

    @Operation(
            summary = "Atualizar um usuário ativo pelo cpf",
            description = "Realiza a atualização do cliente",
            parameters = {
                    @Parameter(name = "cpf", description = "cpf do cliente a ser atualizado", example = "12345678910")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                             "nome": "Nome",
                                             "cpf": 12345678910,
                                             "senha": "senha",
                                             "contaDTO": {
                                                 "tipoConta": "CONTA_POUPANCA",
                                                 "numero": 1234,
                                                 "saldo": 1000
                                             }
                                         }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Cliente não encontrado", value = "\"Cliente não encontrado(a) ou inativo(a)\""),
                                    }
                            )
                    )
            }
    )
    @PutMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> atualizarClientePorCpf(@PathVariable String cpf, @Valid @org.springframework.web.bind.annotation.RequestBody ClienteRegistroDTO dto) {
        return ResponseEntity.ok(clienteService.atualizarCliente(cpf, dto));
    }

    @Operation(
            summary = "Desativar um cliente",
            description = "Desativa um cliente da base de dados a partir do seu cpf",
            parameters = {
                    @Parameter(name = "cpf", description = "cpf do cliente a ser deletado", example = "12345678910")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Cliente não encontrado", value = "\"Cliente não encontrado(a) ou inativo(a)\""),
                                    }
                            )
                    )
            }
    )
    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<Void> desativarCliente(@PathVariable String cpf) {
        clienteService.desativarCliente(cpf);
        return ResponseEntity.noContent().build();
    }
}
