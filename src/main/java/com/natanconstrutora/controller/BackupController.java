package com.natanconstrutora.controller;

import com.natanconstrutora.service.BackupService;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/backups")
@Tag(name = "Backups", description = "APIs para gerenciamento de backups do sistema")
@SecurityRequirement(name = "bearer-jwt")
public class BackupController {

    @Autowired
    private BackupService backupService;

    @Operation(summary = "Listar todos os backups", description = "Retorna todos os backups disponíveis (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de backups retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<String> listarTodos() {
        return backupService.listarBackups();
    }

    @Operation(summary = "Criar novo backup", description = "Cria um novo backup do sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Backup criado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> criarBackup() {
        backupService.realizarBackup();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String nomeArquivo = "backup_" + timestamp + ".sql";
        return ResponseEntity.ok(nomeArquivo);
    }

    @Operation(summary = "Restaurar backup", description = "Restaura um backup específico (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Backup restaurado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Backup não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PostMapping("/restaurar/{nomeArquivo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> restaurarBackup(
            @Parameter(description = "Nome do arquivo de backup", required = true)
            @PathVariable String nomeArquivo) {
        try {
            backupService.restaurarBackup(nomeArquivo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Excluir backup", description = "Remove um backup específico (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Backup removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Backup não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @DeleteMapping("/{nomeArquivo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirBackup(
            @Parameter(description = "Nome do arquivo de backup", required = true)
            @PathVariable String nomeArquivo) {
        try {
            backupService.excluirBackup(nomeArquivo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Download backup", description = "Faz o download de um backup específico (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Download iniciado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Backup não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/download/{nomeArquivo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> downloadBackup(
            @Parameter(description = "Nome do arquivo de backup", required = true)
            @PathVariable String nomeArquivo) {
        return backupService.downloadBackup(nomeArquivo);
    }
} 