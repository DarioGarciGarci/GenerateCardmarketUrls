package org.updateUrls.service;

import org.updateUrls.config.AppConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {

    private FileService() {
    }

    public static void saveOutput(String output)
            throws IOException {

        Files.writeString(
                Path.of(AppConfig.OUTPUT_FILE),
                output
        );
    }
}
