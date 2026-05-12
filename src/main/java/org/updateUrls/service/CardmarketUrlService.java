package org.updateUrls.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.updateUrls.config.AppConfig;
import org.updateUrls.model.CardmarketCard;
import org.updateUrls.util.SlugUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardmarketUrlService {

    private CardmarketUrlService() {
    }

    public static List<CardmarketCard> buildCardmarketCards(
            JsonNode cards
    ) {

        List<CardmarketCard> result = new ArrayList<>();

        Map<String, Integer> slugCounts =
                countSlugs(cards);

        Map<String, Integer> slugVersions =
                new HashMap<>();

        for (JsonNode card : cards) {

            String fullName = getFullName(card);

            if (fullName == null || fullName.isBlank()) {
                continue;
            }

            String baseSlug =
                    SlugUtils.buildBaseSlug(fullName);

            String finalSlug = buildFinalSlug(
                    baseSlug,
                    slugCounts,
                    slugVersions
            );

            String url = buildCardmarketUrl(finalSlug);

            result.add(
                    new CardmarketCard(
                            getId(card),
                            fullName,
                            url,
                            hasCardmarketUrl(card)
                    )
            );
        }

        return result;
    }

    public static void removeCardsWithCardmarketUrl(
            List<CardmarketCard> cards
    ) {

        cards.removeIf(CardmarketCard::hasCardmarketUrl);
    }

    public static String generateOutput(
            List<CardmarketCard> cards
    ) {

        StringBuilder output = new StringBuilder();

        for (CardmarketCard card : cards) {

            output.append(card.url())
                    .append(System.lineSeparator());
        }

        return output.toString();
    }

    private static boolean hasCardmarketUrl(
            JsonNode card
    ) {

        JsonNode node = card.get("cardMarketUrl");

        return node != null
                && !node.isNull()
                && !node.asText().isBlank();
    }

    private static Map<String, Integer> countSlugs(
            JsonNode cards
    ) {

        Map<String, Integer> counts = new HashMap<>();

        for (JsonNode card : cards) {

            String fullName = getFullName(card);

            if (fullName == null || fullName.isBlank()) {
                continue;
            }

            String slug =
                    SlugUtils.buildBaseSlug(fullName);

            counts.put(
                    slug,
                    counts.getOrDefault(slug, 0) + 1
            );
        }

        return counts;
    }

    private static String buildFinalSlug(
            String baseSlug,
            Map<String, Integer> slugCounts,
            Map<String, Integer> slugVersions
    ) {

        int occurrences =
                slugCounts.getOrDefault(baseSlug, 0);

        if (occurrences == 1) {
            return baseSlug;
        }

        int version =
                slugVersions.getOrDefault(baseSlug, 0) + 1;

        slugVersions.put(baseSlug, version);

        return baseSlug + "-V" + version;
    }

    private static String buildCardmarketUrl(
            String slug
    ) {

        return AppConfig.CARDMARKET_BASE_URL
                + slug.replace(".", "");
    }
    private static String getId(JsonNode card) {
        return card.get("id").asText();
    }
    private static String getFullName(JsonNode card) {

        JsonNode translations =
                card.get("translations");

        if (translations == null
                || !translations.isArray()) {
            return null;
        }

        for (JsonNode translation : translations) {

            String fullName = translation
                    .path("fullName")
                    .asText(null);

            if (fullName != null
                    && !fullName.isBlank()) {
                return fullName;
            }
        }

        return null;
    }
}
