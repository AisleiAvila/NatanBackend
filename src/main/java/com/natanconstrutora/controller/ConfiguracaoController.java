package com.natanconstrutora.controller;

import com.natanconstrutora.model.Configuracao;
import com.natanconstrutora.repository.ConfiguracaoRepository;
import com.natanconstrutora.service.ConfiguracaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/configuracoes")
@Tag(name = "Configurações", description = "APIs para gerenciamento de configurações do sistema")
@SecurityRequirement(name = "bearer-jwt")
@PreAuthorize("hasRole('ADMIN')")
public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @Autowired
    private ConfiguracaoService configuracaoService;

    @Operation(summary = "Listar todas as configurações (Repository)", description = "Retorna todas as configurações do sistema diretamente do repositório (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de configurações retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/all")
    public List<Configuracao> listarTodas() {
        return configuracaoRepository.findAll();
    }

    @Operation(summary = "Buscar configuração por chave (Repository)", description = "Retorna uma configuração específica pela sua chave usando o repositório")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuração encontrada"),
        @ApiResponse(responseCode = "404", description = "Configuração não encontrada")
    })
    @GetMapping("/repo/{chave}")
    public ResponseEntity<Configuracao> buscarPorChave(
            @Parameter(description = "Chave da configuração", required = true)
            @PathVariable String chave) {
        Optional<Configuracao> configuracao = configuracaoRepository.findByChave(chave);
        return configuracao.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar nova configuração", description = "Cria uma nova configuração (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuração criada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PostMapping
    public Configuracao criar(
            @Parameter(description = "Dados da nova configuração", required = true)
            @Valid @RequestBody Configuracao configuracao) {
        return configuracaoRepository.save(configuracao);
    }

    @Operation(summary = "Atualizar configuração (Repository)", description = "Atualiza os dados de uma configuração existente usando o repositório (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuração atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Configuração não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PutMapping("/repo/{chave}")
    public ResponseEntity<Configuracao> atualizar(
            @Parameter(description = "Chave da configuração", required = true)
            @PathVariable String chave,
            @Parameter(description = "Novos dados da configuração", required = true)
            @Valid @RequestBody Configuracao configuracaoAtualizada) {
        return configuracaoRepository.findByChave(chave)
                .map(configuracao -> {
                    configuracaoAtualizada.setChave(chave);
                    return ResponseEntity.ok(configuracaoRepository.save(configuracaoAtualizada));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar configuração", description = "Remove uma configuração do sistema (requer permissão de ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuração removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Configuração não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @DeleteMapping("/{chave}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "Chave da configuração", required = true)
            @PathVariable String chave) {
        return configuracaoRepository.findByChave(chave)
                .map(configuracao -> {
                    configuracaoRepository.delete(configuracao);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obter configurações", description = "Retorna todas as configurações do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configurações retornadas com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping
    public ResponseEntity<?> obterConfiguracoes() {
        return ResponseEntity.ok(configuracaoService.obterConfiguracoes());
    }

    @Operation(summary = "Atualizar configuração", description = "Atualiza uma configuração específica do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuração atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PutMapping("/{chave}")
    public ResponseEntity<?> atualizarConfiguracao(
            @Parameter(description = "Chave da configuração", required = true)
            @PathVariable String chave,
            @Parameter(description = "Valor da configuração", required = true)
            @RequestBody String valor) {
        return ResponseEntity.ok(configuracaoService.atualizarConfiguracao(chave, valor));
    }

    @Operation(summary = "Obter configuração", description = "Retorna uma configuração específica do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuração retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Configuração não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @GetMapping("/{chave}")
    public ResponseEntity<?> obterConfiguracao(
            @Parameter(description = "Chave da configuração", required = true)
            @PathVariable String chave) {
        return ResponseEntity.ok(configuracaoService.obterConfiguracao(chave));
    }

    @Operation(summary = "Resetar configurações", description = "Reseta todas as configurações para os valores padrão")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configurações resetadas com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer permissão de ADMIN")
    })
    @PostMapping("/resetar")
    public ResponseEntity<?> resetarConfiguracoes() {
        configuracaoService.resetarConfiguracoes();
        return ResponseEntity.ok().build();
    }
}
