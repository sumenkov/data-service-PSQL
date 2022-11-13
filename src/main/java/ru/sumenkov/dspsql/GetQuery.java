package ru.sumenkov.dspsql;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class GetQuery {

    public static String get(String nameQuery) {
        File fileProperties = new File(Objects.requireNonNull(
                Main.class.getResource("/query.properties")).getFile());
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(fileProperties));
        } catch (IOException e) {
            new SaveError("Не смог прочитать файл запросов к БД");
        }
        return properties.getProperty(nameQuery);
    }
}
