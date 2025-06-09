package com.natanconstrutora.service;

import com.natanconstrutora.dto.SmsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    @Value("${sms.api.key}")
    private String apiKey;

    @Value("${sms.api.url}")
    private String apiUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Autowired
    public SmsService() {
        Twilio.init("seu_account_sid", "seu_auth_token");
    }

    public void enviarSms(SmsRequest request) {
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
                throw new RuntimeException("Erro ao enviar SMS: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao enviar SMS", e);
        }
    }

    public void enviarSmsBoasVindas(String numero, String nome) {
        SmsRequest request = new SmsRequest();
        request.setNumero(numero);
        request.setMensagem("Bem-vindo(a) " + nome + "! Obrigado por se cadastrar em nossa plataforma.");
        enviarSms(request);
    }

    public void enviarSmsRecuperacaoSenha(String numero, String codigo) {
        SmsRequest request = new SmsRequest();
        request.setNumero(numero);
        request.setMensagem("Seu código de recuperação de senha é: " + codigo);
        enviarSms(request);
    }

    public void enviarSmsConfirmacao(String numero, String codigo) {
        SmsRequest request = new SmsRequest();
        request.setNumero(numero);
        request.setMensagem("Seu código de confirmação é: " + codigo);
        enviarSms(request);
    }

    public void enviarSmsNotificacao(String numero, String mensagem) {
        SmsRequest request = new SmsRequest();
        request.setNumero(numero);
        request.setMensagem(mensagem);
        enviarSms(request);
    }

    public void enviar(SmsRequest request) {
        try {
            Message message = Message.creator(
                new PhoneNumber(request.getNumero()),
                new PhoneNumber("+5511999999999"),
                request.getMensagem()
            ).create();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar SMS", e);
        }
    }

    public void enviarBoasVindas(String numero) {
        SmsRequest request = new SmsRequest();
        request.setNumero(numero);
        request.setMensagem("Bem-vindo à Natan Construtora! Estamos felizes em tê-lo conosco.");
        enviar(request);
    }

    public void enviarRecuperacaoSenha(String numero) {
        SmsRequest request = new SmsRequest();
        request.setNumero(numero);
        request.setMensagem("Seu código de recuperação de senha é: 123456");
        enviar(request);
    }

    public void enviarConfirmacao(String numero) {
        SmsRequest request = new SmsRequest();
        request.setNumero(numero);
        request.setMensagem("Seu código de confirmação é: 123456");
        enviar(request);
    }

    public void enviarNotificacao(SmsRequest request) {
        enviar(request);
    }
} 