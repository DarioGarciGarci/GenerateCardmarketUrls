package org.updateUrls.config;

public class AppConfig {

    private AppConfig() {
    }

    public static final int SET_CODE = 12;

    public static final String SET_NAME =
            "Wilds-Unknown";

    public static final boolean UPDATE_API_ENABLED =
            false;

    public static final String API_URL =
            "https://lorcahub.com/api/cards/search" +
                    "?language=en" +
                    "&setCode=" + SET_CODE +
                    "&limit=300" +
                    "&sortOrder=ASC" +
                    "&sortBy=globalId" +
                    "&minimal=false";

    public static final String CARDMARKET_BASE_URL =
            "https://www.cardmarket.com/es/Lorcana/Products/Singles/"
                    + SET_NAME + "/";

    public static final String OUTPUT_FILE =
            "cardmarket-urls.txt";

    public static final String API_BASE_URL =
            "https://lorcahub.com/api/cards/";

    public static final String AUTH_TOKEN =
            "TOKEN";
}
