package ru.sumenkov.dspsql;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        File file = new File("src/main/resources/db.properties");

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
            Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);
            conn.close();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}