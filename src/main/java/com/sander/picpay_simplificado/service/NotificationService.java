package com.sander.picpay_simplificado.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sander.picpay_simplificado.dto.NotificationDto;
import com.sander.picpay_simplificado.entity.Wallet;
import com.sander.picpay_simplificado.exception.NotificationException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class NotificationService {


    public void notification(Wallet user, String message ){

        String email = user.getEmail();


        NotificationDto notification = new NotificationDto(email,message);

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            String requestBody = objectMapper.writeValueAsString(notification);


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://run.mocky.io/v3/41f18625-fe42-4831-b686-d360dc8ee0ed"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

          HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200){
            System.out.println("Notificação enviada com sucesso!");
        }else {
            throw new NotificationException("Erro ao enviar notificação. Status: " + response.statusCode()
                    + " Resposta: "+ response.body());
        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
