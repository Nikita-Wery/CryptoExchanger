package ru.exchanger.utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class SQLoader {

    /**
     * Загружает SQL-файл из папки resources и возвращает его как строку.
     *
     *  resourcePath путь относительно resources, например "db/changelog/DDL/INSERT_NEW_USER.sql"
     */
    public static String loadSQL(String resourcePath) {
        try (InputStream is = SQLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new RuntimeException("SQL resource not found: " + resourcePath);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load SQL from resource: " + resourcePath, e);
        }
    }
}
