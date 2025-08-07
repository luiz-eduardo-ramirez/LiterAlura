package br.com.alura.literAlura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class APIconsumption {
    //um objeto usado para registrar (logar) informações importantes,
    // como mensagens de depuração, erros e outros eventos significativos
    // que ocorrem durante a execução de um programa.
    private static Logger LOGGER = Logger.getLogger(APIconsumption.class.getName());

    public String getData(String urlAddress) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlAddress))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() != 200) {
            LOGGER.severe("Failed to fetch data. HTTP status code: " + response.statusCode());
            throw new RuntimeException("Failed to fetch data. HTTP status code: " + response.statusCode());
        }


        String json = response.body();
        if (json == null || json.isEmpty()) {
            LOGGER.severe("Empty response from API");
            throw new RuntimeException("Empty response from API");
        }

        return json;
    }
}
