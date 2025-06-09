package com.natanconstrutora.service;

import com.natanconstrutora.dto.SmsRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class SmsServiceFake extends SmsService {
    @Override
    public void enviarSms(SmsRequest request) {
        System.out.println("[FAKE SMS] Para: " + request.getNumero() + ", Mensagem: " + request.getMensagem());
    }
}

