package com.natanconstrutora.dto;

import lombok.Data;

@Data
public class PushNotificationRequest {
    private String titulo;
    private String mensagem;
    private String token;
    private String topico;
} 