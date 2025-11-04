package com.sitionix.bffssox.it.infra.utils.loader;

import java.io.IOException;
import java.io.InputStream;

public class JsonLoader {
    private static final String PATH_EXPECTED = "json%s";

    static String getFile(final ClassLoader classLoader, final String path, final String fileName) {
        final String filePath = String.format(PATH_EXPECTED, path);

        try(final InputStream isLoader = classLoader.getResourceAsStream(String.format(filePath, fileName))) {
            assert isLoader != null;
            return new String(isLoader.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to get file: %s", fileName));
        }
    }

    public static String loadCustomFile(final String fileName) {
        final ClassLoader classLoader = JsonLoader.class.getClassLoader();
        return getFile(classLoader, "/custom/%s", fileName);
    }
}
