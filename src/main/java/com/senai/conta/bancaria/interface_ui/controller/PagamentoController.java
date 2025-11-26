package com.senai.conta.bancaria.interface_ui.controller;

import com.senai.conta.bancaria.application.dto.*;
import com.senai.conta.bancaria.application.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Pagamento", description = "Gerenciamento de pagamentos dos clientes do banco")
@RestController
@RequestMapping("/pagamento")
@RequiredArgsConstructor
public class PagamentoController {
    private final PagamentoService pagamentoService;

    @Operation(
            summary = "Cadastrar um novo pagamento",
            description = "Realiza o cadastro do pagamento",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = PagamentoDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                            "numeroConta": 1234,
                                             "boleto": "123",
                                             "valorPago": 123,
                                             "taxas": [
                                                {
                                                    "descricao": "IOF",
                                                     "percentual": 0.2,
                                                     "valorFixo": 1
                                                },
                                                {
                                                    "descricao": "TARIFA_BANCARIA",
                                                     "percentual": 0.3,
                                                     "valorFixo": 1
                                                }
                                             ]
                                         }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamento realizado com sucesso"),
            }
    )
    @PostMapping
    public ResponseEntity<PagamentoDTO> registrarPagamento(@Valid @RequestBody PagamentoDTO dto) {
        PagamentoDTO novoPagamento = pagamentoService.registrarPagamento(dto);
        return ResponseEntity.created(
                URI.create("/api/pagamento/boleto/" + novoPagamento.boleto())
        ).body(novoPagamento);
    }

    @Operation(
            summary = "Listar todas os pagamento",
            description = "Retorna todos os pagamentos cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> listarPagamentos() {
        return ResponseEntity.ok(pagamentoService.listarPagamentos());
    }

    @Operation(
            summary = "Buscar um pagamento pelo boleto",
            description = "Retorna um pagamento cadastrado",
            parameters = {
                    @Parameter(name = "boleto", description = "boleto do pagamento a ser buscado", example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamento retornado com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pagamento não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(name = "Pagamento não encontrado", value = "\"Pagamento com boleto 123 não encontrado.\"")
                            )
                    )
            }
    )
    @GetMapping("/boleto/{boleto}")
    public ResponseEntity<PagamentoDTO> buscarPagamento(@PathVariable String boleto) {
        return ResponseEntity.ok(pagamentoService.buscarPagamentoPorBoleto(boleto));
    }
}