package org.updateUrls.util;

public class SlugUtils {

    private SlugUtils() {
    }

    public static String buildBaseSlug(
            String fullName
    ) {

        String slug = fullName.trim();

        slug = normalizeDashSeparators(slug);

        slug = removeSpecialCharacters(slug);

        slug = normalizeSpaces(slug);

        slug = slug.replace(" ", "-");

        slug = removeDuplicateDashes(slug);

        slug = trimDashes(slug);

        return slug;
    }

    private static String normalizeDashSeparators(
            String value
    ) {

        return value.replaceAll("\\s*-\\s*", "-");
    }

    private static String removeSpecialCharacters(
            String value
    ) {

        return value.replaceAll(
                "[^a-zA-Z0-9\\s.-]",
                ""
        );
    }

    private static String normalizeSpaces(
            String value
    ) {

        return value.replaceAll("\\s+", " ");
    }

    private static String removeDuplicateDashes(
            String value
    ) {

        return value.replaceAll("-{2,}", "-");
    }

    private static String trimDashes(
            String value
    ) {

        return value.replaceAll("^-|-$", "");
    }
}
