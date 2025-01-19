package com.sander.picpay_simplificado.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sander.picpay_simplificado.dto.AuthorizeDto;
import com.sander.picpay_simplificado.entity.Wallet;
import com.sander.picpay_simplificado.exception.ValidationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class AuthorizationService {

    AuthorizeDto authorizeDto = null;


    public AuthorizeDto getAuthorization() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://util.devi.tools/api/v2/authorize")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            authorizeDto = mapper.readValue(response.body(), AuthorizeDto.class);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return authorizeDto;
    }

    public AuthorizeDto authorizar(Wallet wallet, BigDecimal value) {
        AuthorizeDto authResponse = getAuthorization();
        var responseStatus = authorizeDto.status();
        var responseAthorization = authorizeDto.data().authorization();
        if (!"success".equals(responseStatus) || !responseAthorization) {
            throw new ValidationException("Autorização falhou: status = " + responseStatus
                    + ", authorization=" + responseAthorization);
        }
        return authResponse;
    }

}
