package org.updateUrls.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.updateUrls.model.CardmarketCard;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static org.updateUrls.config.AppConfig.API_BASE_URL;
import static org.updateUrls.config.AppConfig.AUTH_TOKEN;

public class UpdateCardmarketUrlApiService {

    private static final HttpClient HTTP_CLIENT =
            HttpClient.newHttpClient();

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();

    private UpdateCardmarketUrlApiService() {
    }

    public static void updateCards(
            List<CardmarketCard> cards
    ) {

        for (CardmarketCard card : cards) {

            try {

                updateCard(card);

            } catch (Exception e) {

                printError(card, e);
            }
        }
    }

    /**
     * Calls LorcaHub PATCH endpoint.
     */
    private static void updateCard(
            CardmarketCard card
    ) throws IOException, InterruptedException {

        String endpoint =
                API_BASE_URL
                        + card.id()
                        + "/cardmarket-url";

        String body = OBJECT_MAPPER.writeValueAsString(
                Map.of(
                        "cardMarketUrl",
                        card.url()
                )
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .method(
                        "PATCH",
                        HttpRequest.BodyPublishers.ofString(body)
                )
                .header(
                        "accept",
                        "application/json, text/plain, */*"
                )
                .header(
                        "accept-language",
                        "es-ES,es;q=0.9,en;q=0.8"
                )
                .header(
                        "authorization",
                        "Bearer " + AUTH_TOKEN
                )
                .header(
                        "content-type",
                        "application/json"
                )
                .header(
                        "origin",
                        "https://lorcahub.com"
                )
                .header(
                        "referer",
                        "https://lorcahub.com/cards/" + card.id()
                )
                .header(
                        "user-agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36"
                )
                .header(
                        "x-requested-with",
                        "XMLHttpRequest"
                )
                .build();

        HttpResponse<String> response =
                HTTP_CLIENT.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        validateResponse(card, response);
    }

    /**
     * Validates API response.
     */
    private static void validateResponse(
            CardmarketCard card,
            HttpResponse<String> response
    ) {

        if (response.statusCode() >= 200
                && response.statusCode() < 300) {

            System.out.println(
                    "OK -> " + card.fullName()
            );

            return;
        }

        throw new RuntimeException(
                """
                Error updating card
                
                Card: %s
                Status: %s
                Response: %s
                URL: %s
                """
                        .formatted(
                                card.fullName(),
                                response.statusCode(),
                                response.body(),
                                card.url()
                        )
        );
    }

    /**
     * Prints detailed error information.
     */
    private static void printError(
            CardmarketCard card,
            Exception exception
    ) {

        System.out.printf(
                """
                        
                        ERROR updating card
                        
                        Card: %s
                        Message: %s
                        URL: %s
                        
                        %n""", card.fullName(),
                exception.getMessage(),
                card.url()
        );
    }
}