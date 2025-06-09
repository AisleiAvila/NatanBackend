package com.natanconstrutora.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BackupService {

    @Value("${backup.directory}")
    private String backupDirectory;

    @Transactional
    public void realizarBackup() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String backupFileName = "backup_" + timestamp + ".sql";
            Path backupPath = Paths.get(backupDirectory, backupFileName);

            // Criar diretório de backup se não existir
            Files.createDirectories(backupPath.getParent());

            // Executar comando pg_dump
            ProcessBuilder processBuilder = new ProcessBuilder(
                "pg_dump",
                "-h", "localhost",
                "-U", "postgres",
                "-d", "natan_construtora",
                "-F", "c",
                "-f", backupPath.toString()
            );

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException("Falha ao realizar backup");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao realizar backup", e);
        }
    }

    public List<String> listarBackups() {
        try {
            Path backupPath = Paths.get(backupDirectory);
            if (!Files.exists(backupPath)) {
                return new ArrayList<>();
            }

            return Files.list(backupPath)
                .filter(path -> path.toString().endsWith(".sql"))
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao listar backups", e);
        }
    }

    @Transactional
    public void restaurarBackup(String nomeArquivo) {
        try {
            Path backupPath = Paths.get(backupDirectory, nomeArquivo);
            if (!Files.exists(backupPath)) {
                throw new RuntimeException("Arquivo de backup não encontrado");
            }

            // Executar comando pg_restore
            ProcessBuilder processBuilder = new ProcessBuilder(
                "pg_restore",
                "-h", "localhost",
                "-U", "postgres",
                "-d", "natan_construtora",
                "-c",
                backupPath.toString()
            );

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException("Falha ao restaurar backup");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao restaurar backup", e);
        }
    }

    public void excluirBackup(String nomeArquivo) {
        try {
            Path backupPath = Paths.get(backupDirectory, nomeArquivo);
            if (!Files.exists(backupPath)) {
                throw new RuntimeException("Arquivo de backup não encontrado");
            }

            Files.delete(backupPath);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao excluir backup", e);
        }
    }

    public ResponseEntity<byte[]> downloadBackup(String nomeArquivo) {
        try {
            Path backupPath = Paths.get(backupDirectory, nomeArquivo);
            if (!Files.exists(backupPath)) {
                return ResponseEntity.notFound().build();
            }
            byte[] arquivo = Files.readAllBytes(backupPath);
            return ResponseEntity.ok(arquivo);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer download do backup", e);
        }
    }
} 