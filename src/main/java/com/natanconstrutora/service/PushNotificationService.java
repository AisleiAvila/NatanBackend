package com.natanconstrutora.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.natanconstrutora.dto.PushNotificationRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public void enviarNotificacao(PushNotificationRequest request) {
        try {
            Notification notification = Notification.builder()
                .setTitle(request.getTitulo())
                .setBody(request.getMensagem())
                .build();

            Message message = Message.builder()
                .setNotification(notification)
                .setToken(request.getToken())
                .build();

            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException("Erro ao enviar notificação push", e);
        }
    }

    public void enviarNotificacaoParaTopico(PushNotificationRequest request) {
        try {
            Notification notification = Notification.builder()
                .setTitle(request.getTitulo())
                .setBody(request.getMensagem())
                .build();

            Message message = Message.builder()
                .setNotification(notification)
                .setTopic(request.getTopico())
                .build();

            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException("Erro ao enviar notificação push para tópico", e);
        }
    }

    public void enviarNotificacaoParaDispositivos(PushNotificationRequest request, List<String> tokens) {
        try {
            Notification notification = Notification.builder()
                .setTitle(request.getTitulo())
                .setBody(request.getMensagem())
                .build();

            List<Message> messages = tokens.stream()
                .map(token -> Message.builder()
                    .setNotification(notification)
                    .setToken(token)
                    .build())
                .collect(Collectors.toList());

            firebaseMessaging.sendAll(messages);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException("Erro ao enviar notificação push para múltiplos dispositivos", e);
        }
    }

    public void enviar(PushNotificationRequest request) {
        try {
            Notification notification = Notification.builder()
                .setTitle(request.getTitulo())
                .setBody(request.getMensagem())
                .build();

            Message message = Message.builder()
                .setNotification(notification)
                .setToken(request.getToken())
                .build();

            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException("Erro ao enviar notificação push", e);
        }
    }

    public void enviarParaTodos(PushNotificationRequest request) {
        try {
            Notification notification = Notification.builder()
                .setTitle(request.getTitulo())
                .setBody(request.getMensagem())
                .build();

            Message message = Message.builder()
                .setNotification(notification)
                .setTopic("todos")
                .build();

            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException("Erro ao enviar notificação push para todos", e);
        }
    }

    public void registrarDispositivo(String token) {
        try {
            firebaseMessaging.subscribeToTopic(Collections.singletonList(token), "todos");
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException("Erro ao registrar dispositivo", e);
        }
    }

    public void removerDispositivo(String token) {
        try {
            firebaseMessaging.unsubscribeFromTopic(Collections.singletonList(token), "todos");
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException("Erro ao remover dispositivo", e);
        }
    }
} 