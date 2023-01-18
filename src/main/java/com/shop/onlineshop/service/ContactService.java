package com.shop.onlineshop.service;

import com.shop.onlineshop.dto.Contact;
import jakarta.ws.rs.core.UriBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
@Slf4j
public class ContactService {
    private static final String CHAT_ID = "-1001610411225";
    private static final String TOKEN = "5930685010:AAHesMW2WQP0rg7U1I61aLuKjvXDs0J1ADg";


    public void sendMessage(Contact contact) throws IOException, InterruptedException {

        String message = "Заявка \n Имя: " + contact.getName() + "\n Почта: " + contact.getEmail() + "\n Сообщение: " + contact.getMessage();

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();
        UriBuilder builder = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/{TOKEN}/sendMessage")
                .queryParam("chat_id", CHAT_ID)
                .queryParam("text", message);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + TOKEN))
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("======" + response.statusCode() + " ++++++ " + response.body());
    }


}
