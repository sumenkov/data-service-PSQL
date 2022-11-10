package ru.sumenkov.dspsql;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.sumenkov.dspsql.model.input.JsonInputStatModel;
import ru.sumenkov.dspsql.model.output.JsonOutputStatModel;
import ru.sumenkov.dspsql.repository.StatRepository;
import ru.sumenkov.dspsql.repository.impl.InitRepositoryImpl;
import ru.sumenkov.dspsql.repository.impl.StatRepositoryImpl;
import ru.sumenkov.dspsql.service.SaveJson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonInputStatModel inputStatModel = objectMapper.readValue(
                    new File("src/test/input_stat.json"),
                    JsonInputStatModel.class);

            String startDate = inputStatModel.getStartDate();
            String endDate = inputStatModel.getEndDate();


            File file = new File("src/main/resources/db.properties");
            Properties properties = new Properties();
            StatRepository statRepository;

            JsonOutputStatModel jsonOutputStatModel = new JsonOutputStatModel();
            jsonOutputStatModel.setTotalDays(getTotalDays(startDate, endDate));

            properties.load(new FileReader(file));
            try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties)) {
                boolean init = new InitRepositoryImpl(conn).initTables();
                if (!init) {
                    log.info("App stopped: fail init");
                }

                statRepository = new StatRepositoryImpl(conn, startDate, endDate);
                jsonOutputStatModel.setCustomers(statRepository.getStatFromDB());
            }

            SaveJson.save(jsonOutputStatModel);

        } catch (IOException | SQLException e) {
            log.log(Level.SEVERE, "Fail open connect", e);
        }
    }

    private static long getTotalDays(String startDate, String endDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDateTime date1 = LocalDate.parse(startDate, dtf).atStartOfDay();
            LocalDateTime date2 = LocalDate.parse(endDate, dtf).atStartOfDay();

            int days = 0;
            while(date1.isBefore(date2.plusDays(1))) {
                if (!DayOfWeek.SATURDAY.equals(date1.getDayOfWeek())
                        && !DayOfWeek.SUNDAY.equals(date1.getDayOfWeek())) {
                    days++;
                }
                date1 = date1.plusDays(1);
            }
            return days;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
