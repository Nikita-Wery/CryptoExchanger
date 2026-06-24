package ru.exchanger.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    public static String getProperty(String property) {

        Properties properties = new Properties();

        try (InputStream input = PropertyLoader.class.getClassLoader().getResourceAsStream("properties/access.properties")) {
            if (input == null) {
                System.out.println("Файл access.properties не найден в classpath.");
            } else {
                properties.load(input);
                System.out.println("Файл успешно загружен из classpath");
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return properties.getProperty(property);
    }
}
