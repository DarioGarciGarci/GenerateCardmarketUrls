package org.updateUrls.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.updateUrls.config.AppConfig;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CardDownloadService {

    private static final HttpClient HTTP_CLIENT =
            HttpClient.newHttpClient();

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();

    private CardDownloadService() {
    }

    public static JsonNode downloadCards()
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AppConfig.API_URL))
                .GET()
                .build();

        HttpResponse<String> response = HTTP_CLIENT.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        validateResponse(response);

        JsonNode root =
                OBJECT_MAPPER.readTree(response.body());

        return root.get("cards");
    }

    private static void validateResponse(
            HttpResponse<String> response
    ) {

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "HTTP error: " + response.statusCode()
            );
        }
    }
}
