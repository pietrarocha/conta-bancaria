package com.senai.conta.bancaria.interface_ui.controller;


import com.senai.conta.bancaria.application.dto.*;
import com.senai.conta.bancaria.application.service.ContaService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Conta", description = "Gerenciamento de contas dos clientes do banco")
@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService contaService;

    @Operation(
            summary = "Listar todas as contas ativas",
            description = "Retorna todos as contas cadastradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<List<ContaResumoDTO>> listarConta() {
        return ResponseEntity.ok(contaService.listarConta());
    }

    @Operation(
            summary = "Buscar uma conta ativa pelo numero",
            description = "Retorna uma conta cadastrada",
            parameters = {
                    @Parameter(name = "numero", description = "numero da conta a ser buscada", example = "1234-5")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conta retornada com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(name = "Conta não encontrada", value = "\"Conta com numero 1234-5 não encontrada.\"")
                            )
                    )
            }
    )
    @GetMapping("/numero/{numero}")
    public ResponseEntity<ContaResumoDTO> buscarConta(@PathVariable String numero) {
        return ResponseEntity.ok(contaService.buscarContaPorNumero(numero));
    }

    @Operation(
            summary = "Atualizar uma conta ativa pelo numero",
            description = "Realiza a atualização da conta",
            parameters = {
                    @Parameter(name = "numero", description = "numero da conta a ser atualizada", example = "1234-5")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                             "tipoConta": "CONTA_CORRENTE",
                                             "numero": "1234-5",
                                             "saldo": 123,
                                             "limite": 123,
                                             "taxa": 123,
                                             "rendimento": 123
                                         }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Conta não encontrada", value = "\"Conta não encontrado(a) ou inativo(a)\""),
                                    }
                            )
                    )
            }
    )
    @PutMapping("/numero/{numero}")
    public ResponseEntity<ContaResumoDTO> atualizarConta(@PathVariable String numero, @Valid @org.springframework.web.bind.annotation.RequestBody ContaAtualizacaoDTO dto) {
        return ResponseEntity.ok(contaService.atualizarConta(numero, dto));
    }

    @Operation(
            summary = "Sacar valor de uma conta ativa pelo numero",
            description = "Realiza o saque da conta",
            parameters = {
                    @Parameter(name = "numero", description = "numero da conta para sacar", example = "1234-5")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                             "valor": 123
                                         }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Saque realizada com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Conta não encontrada", value = "\"Conta não encontrado(a) ou inativo(a)\""),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Valores negativos não são permitidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Valores negativos não são permitidos", value = "\"Não é possível realizar a operação com valores negativos\""),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Saldo insuficiente para a operação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Saldo insuficiente para a operação", value = "\"Saldo insuficiente para a operação\""),
                                    }
                            )
                    )
            }
    )
    @PostMapping("/numero/{numero}/sacar")
    public ResponseEntity<ContaResumoDTO> sacar(@PathVariable String numero, @Valid @org.springframework.web.bind.annotation.RequestBody ValorSaqueDepositoDTO valor) {
        return ResponseEntity.ok(contaService.sacar(numero, valor));
    }

    @Operation(
            summary = "Depositar valor para uma conta ativa pelo numero",
            description = "Realiza o deposito da conta",
            parameters = {
                    @Parameter(name = "numero", description = "numero da conta a ser para depositar", example = "1234-5")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                             "valor": 123
                                         }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Deposito realizado com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Conta não encontrada", value = "\"Conta não encontrado(a) ou inativo(a)\""),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Valores negativos não são permitidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Valores negativos não são permitidos", value = "\"Não é possível realizar a operação com valores negativos\""),
                                    }
                            )
                    )
            }
    )
    @PostMapping("/numero/{numero}/depositar")
    public ResponseEntity<ContaResumoDTO> depositar(@PathVariable String numero, @Valid @org.springframework.web.bind.annotation.RequestBody ValorSaqueDepositoDTO dto) {
        return ResponseEntity.ok(contaService.depositar(numero, dto));
    }

    @Operation(
            summary = "Transferir valor de uma conta ativa pelo numero para outra",
            description = "Realiza a transferencia da uma conta para outra",
            parameters = {
                    @Parameter(name = "numero", description = "numero da conta para sacar", example = "1234-5")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                             "numeroContaDestino": "1234-6",
                                             "valor": 123
                                         }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transferencia realizada com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Conta não encontrada", value = "\"Conta não encontrado(a) ou inativo(a)\""),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Não é possível transferir para a mesma conta",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Não é possível transferir para a mesma conta", value = "\"Não é possível transferir para a mesma conta\""),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Valores negativos não são permitidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Valores negativos não são permitidos", value = "\"Não é possível realizar a operação com valores negativos\""),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Saldo insuficiente para a operação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Saldo insuficiente para a operação", value = "\"Saldo insuficiente para a operação\""),
                                    }
                            )
                    )
            }
    )
    @PostMapping("/numero/{numero}/transferir")
    public ResponseEntity<ContaResumoDTO> transferir(@PathVariable String numero, @Valid @org.springframework.web.bind.annotation.RequestBody TransferenciaDTO dto) {
        return ResponseEntity.ok(contaService.transferir(numero, dto));
    }

    @Operation(
            summary = "Aplicar rendimento em uma conta poupanca ativa pelo numero",
            description = "Aplica o rendimento da uma conta poupanca",
            parameters = {
                    @Parameter(name = "numero", description = "numero da conta para aplicar rendimento", example = "1234-5")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rendimento aplicado com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Conta não encontrada", value = "\"Conta não encontrado(a) ou inativo(a)\""),
                                    }
                            )
                    )
            }
    )
    @PostMapping("/numero/{numero}/rendimento")
    public ResponseEntity<ContaResumoDTO> aplicarRendimento(@PathVariable String numero) {
        return ResponseEntity.ok(contaService.aplicarRendimento(numero));
    }

    @Operation(
            summary = "Desativar uma conta",
            description = "Desativa uma conta da base de dados a partir do seu numero",
            parameters = {
                    @Parameter(name = "numero", description = "numero da conta a ser deletada", example = "1234-5")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Conta removida com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Conta não encontrada", value = "\"Conta não encontrado(a) ou inativo(a)\""),
                                    }
                            )
                    )
            }
    )
    @DeleteMapping("/numero/{numero}")
    public ResponseEntity<Void> desativarConta(@PathVariable String numero) {
        contaService.desativarConta(numero);
        return ResponseEntity.noContent().build();
    }
}
