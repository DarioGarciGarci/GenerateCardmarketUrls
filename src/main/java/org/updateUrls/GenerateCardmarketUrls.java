package org.updateUrls;

import com.fasterxml.jackson.databind.JsonNode;
import org.updateUrls.model.CardmarketCard;
import org.updateUrls.service.CardDownloadService;
import org.updateUrls.service.CardmarketUrlService;
import org.updateUrls.service.FileService;
import org.updateUrls.service.UpdateCardmarketUrlApiService;

import java.io.IOException;
import java.util.List;

import static org.updateUrls.config.AppConfig.UPDATE_API_ENABLED;

public class GenerateCardmarketUrls {

    public static void main(String[] args) {

        try {

            JsonNode cards =
                    CardDownloadService.downloadCards();

            List<CardmarketCard> cardmarketCards =
                    CardmarketUrlService.buildCardmarketCards(cards);

            CardmarketUrlService.removeCardsWithCardmarketUrl(
                    cardmarketCards
            );

            String output =
                    CardmarketUrlService.generateOutput(cardmarketCards);

            FileService.saveOutput(output);

            if (UPDATE_API_ENABLED){
                UpdateCardmarketUrlApiService.updateCards(
                        cardmarketCards
                );
            }

            System.out.println("Process completed successfully");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
