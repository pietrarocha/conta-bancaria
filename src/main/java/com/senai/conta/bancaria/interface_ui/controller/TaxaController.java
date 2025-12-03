package com.senai.conta.bancaria.interface_ui.controller;

import com.senai.conta.bancaria.application.dto.TaxaDTO;
import com.senai.conta.bancaria.application.service.TaxaService;
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

@Tag(name = "Taxa", description = "Gerenciamento de taxas do banco")
@RestController
@RequestMapping("/taxa")
@RequiredArgsConstructor
public class TaxaController {
    private final TaxaService taxaService;

    @Operation(
            summary = "Cadastrar uma nova taxa",
            description = "Realiza o cadastro da taxa",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TaxaDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                            "descricao": "IOF",
                                            "tipoPagamento": ["LUZ", "AGUA"]
                                            "percentual": 0.1,
                                            "valorFixo": 123
                                         }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Taxa criada com sucesso"),
            }
    )
    @PostMapping
    public ResponseEntity<TaxaDTO> criarTaxa(@Valid @RequestBody TaxaDTO dto) {
        TaxaDTO novaTaxa = taxaService.criarTaxa(dto);
        return ResponseEntity.created(
                URI.create("/api/taxa/descricao/" + novaTaxa.descricao())
        ).body(novaTaxa);
    }

    @Operation(
            summary = "Listar todas as taxas",
            description = "Retorna todas as taxas cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<List<TaxaDTO>> listarTaxas() {
        return ResponseEntity.ok(taxaService.listarTaxas());
    }

    @Operation(
            summary = "Buscar uma taxa pelo id",
            description = "Retorna uma taxa cadastrada",
            parameters = {
                    @Parameter(name = "id", description = "id da taxa a ser buscado", example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Taxa retornada com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Taxa não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(name = "Taxa não encontrada", value = "\"Taxa com id 123 não encontrada.\"")
                            )
                    )
            }
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<TaxaDTO> buscarTaxaPorId(@PathVariable String id) {
        return ResponseEntity.ok(taxaService.buscarTaxaPorId(id));
    }

    @Operation(
            summary = "Atualizar uma taxa pelo id",
            description = "Realiza a atualização da taxa",
            parameters = {
                    @Parameter(name = "id", description = "id da taxa a ser atualizada", example = "12345")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TaxaDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                            "descricao": "IOF",
                                            "tipoPagamento": ["LUZ"]
                                             "percentual": 0.1,
                                             "valorFixo": 123
                                         }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Taxa não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Taxa não encontrada", value = "\"Taxa não encontrado(a) ou inativo(a)\""),
                                    }
                            )
                    )
            }
    )
    @PutMapping("/id/{id}")
    public ResponseEntity<TaxaDTO> atualizarTaxa(@PathVariable String id, @Valid @RequestBody TaxaDTO dto) {
        return ResponseEntity.ok(taxaService.atualizarTaxa(id, dto));
    }

    @Operation(
            summary = "Deletar uma taxa",
            description = "Deleta uma taxa da base de dados a partir do seu id",
            parameters = {
                    @Parameter(name = "id", description = "id da taxa a ser deletada", example = "12345")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Taxa removida com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Taxa não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Taxa não encontrada", value = "\"Taxa não encontrado(a) ou inativo(a)\""),
                                    }
                            )
                    )
            }
    )
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> desativarConta(@PathVariable String id) {
        taxaService.deletarTaxa(id);
        return ResponseEntity.noContent().build();
    }
}