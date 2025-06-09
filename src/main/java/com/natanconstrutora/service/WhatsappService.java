package com.natanconstrutora.service;

import com.natanconstrutora.dto.WhatsappRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WhatsappService {

    @Value("${whatsapp.api.key}")
    private String apiKey;

    @Value("${whatsapp.api.url}")
    private String apiUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void enviarMensagem(WhatsappRequest request) {
        try {
            String jsonBody = String.format(
                "{\"numero\": \"%s\", \"mensagem\": \"%s\"}",
                request.getNumero(),
                request.getMensagem()
            );

            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Erro ao enviar mensagem WhatsApp: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao enviar mensagem WhatsApp", e);
        }
    }

    public void enviarMensagemBoasVindas(String numero, String nome) {
        WhatsappRequest request = new WhatsappRequest();
        request.setNumero(numero);
        request.setMensagem("Bem-vindo(a) " + nome + "! Obrigado por se cadastrar em nossa plataforma.");
        enviarMensagem(request);
    }

    public void enviarMensagemRecuperacaoSenha(String numero, String codigo) {
        WhatsappRequest request = new WhatsappRequest();
        request.setNumero(numero);
        request.setMensagem("Seu código de recuperação de senha é: " + codigo);
        enviarMensagem(request);
    }

    public void enviarMensagemConfirmacao(String numero, String codigo) {
        WhatsappRequest request = new WhatsappRequest();
        request.setNumero(numero);
        request.setMensagem("Seu código de confirmação é: " + codigo);
        enviarMensagem(request);
    }

    public void enviarMensagemNotificacao(String numero, String mensagem) {
        WhatsappRequest request = new WhatsappRequest();
        request.setNumero(numero);
        request.setMensagem(mensagem);
        enviarMensagem(request);
    }
} 