package com.natanconstrutora.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ArquivoService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public String salvarArquivo(MultipartFile arquivo) throws IOException {
        String nomeArquivo = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename();
        Path diretorio = Paths.get(uploadDir);
        
        if (!Files.exists(diretorio)) {
            Files.createDirectories(diretorio);
        }

        Path arquivoPath = diretorio.resolve(nomeArquivo);
        Files.copy(arquivo.getInputStream(), arquivoPath);

        return nomeArquivo;
    }

    public void excluirArquivo(String nomeArquivo) throws IOException {
        Path arquivoPath = Paths.get(uploadDir, nomeArquivo);
        Files.deleteIfExists(arquivoPath);
    }

    public byte[] carregarArquivo(String nomeArquivo) throws IOException {
        Path arquivoPath = Paths.get(uploadDir, nomeArquivo);
        return Files.readAllBytes(arquivoPath);
    }

    public String upload(MultipartFile arquivo) {
        try {
            if (!Files.exists(Paths.get(uploadDir))) {
                Files.createDirectories(Paths.get(uploadDir));
            }
            String nomeArquivo = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename();
            Path arquivoPath = Paths.get(uploadDir).resolve(nomeArquivo);
            Files.copy(arquivo.getInputStream(), arquivoPath);
            return nomeArquivo;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload do arquivo", e);
        }
    }

    public ResponseEntity<byte[]> download(String nome) {
        try {
            Path arquivoPath = Paths.get(uploadDir).resolve(nome);
            byte[] arquivo = Files.readAllBytes(arquivoPath);
            return ResponseEntity.ok(arquivo);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public boolean excluir(String nome) {
        try {
            Path arquivoPath = Paths.get(uploadDir).resolve(nome);
            return Files.deleteIfExists(arquivoPath);
        } catch (IOException e) {
            return false;
        }
    }

    public ResponseEntity<byte[]> listar() {
        try {
            if (!Files.exists(Paths.get(uploadDir))) {
                return ResponseEntity.ok(new byte[0]);
            }
            return ResponseEntity.ok(Files.readAllBytes(Paths.get(uploadDir)));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 