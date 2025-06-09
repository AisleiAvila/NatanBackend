package com.natanconstrutora.controller;

import com.natanconstrutora.service.ArquivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/arquivos")
@Tag(name = "Arquivos", description = "APIs para gerenciamento de arquivos")
@SecurityRequirement(name = "bearer-jwt")
public class ArquivoController {

    @Autowired
    private ArquivoService arquivoService;

    @Operation(summary = "Fazer upload de arquivo", description = "Faz upload de um arquivo para o sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arquivo enviado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Arquivo inválido")
    })
    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @Parameter(description = "Arquivo a ser enviado", required = true)
            @RequestParam("arquivo") MultipartFile arquivo) {
        return ResponseEntity.ok(arquivoService.upload(arquivo));
    }

    @Operation(summary = "Download de arquivo", description = "Faz download de um arquivo específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arquivo baixado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Arquivo não encontrado")
    })
    @GetMapping("/download/{nome}")
    public ResponseEntity<byte[]> download(
            @Parameter(description = "Nome do arquivo", required = true)
            @PathVariable String nome) {
        return arquivoService.download(nome);
    }

    @Operation(summary = "Excluir arquivo", description = "Remove um arquivo do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Arquivo removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Arquivo não encontrado")
    })
    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "Nome do arquivo", required = true)
            @PathVariable String nome) {
        if (arquivoService.excluir(nome)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Listar arquivos", description = "Retorna a lista de todos os arquivos (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de arquivos retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> listar() {
        return arquivoService.listar();
    }
} 