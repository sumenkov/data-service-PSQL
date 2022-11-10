package ru.sumenkov.dspsql;

import ru.sumenkov.dspsql.repository.impl.InitRepositoryImpl;
import ru.sumenkov.dspsql.repository.impl.StatRepositoryImpl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {

        File file = new File("src/main/resources/db.properties");
        Properties properties = new Properties();

        try {
            properties.load(new FileReader(file));
            try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties)) {
                boolean init = new InitRepositoryImpl(conn).initTables();
                if (!init) {
                    log.info("App stopped: fail init");
                }

//                String startDate = LocalDate.parse("2022-10-01").format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                System.out.println(new StatRepositoryImpl(conn, "2022-10-01", "2022-10-21").getStat());

            }
        } catch (IOException | SQLException e) {
            log.log(Level.SEVERE, "Fail open connect", e);
        }
    }
}