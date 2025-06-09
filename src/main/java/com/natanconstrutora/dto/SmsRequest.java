package com.natanconstrutora.dto;

import lombok.Data;

@Data
public class SmsRequest {
    private String numero;
    private String mensagem;
} 