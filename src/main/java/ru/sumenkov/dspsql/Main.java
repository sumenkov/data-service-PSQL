package ru.sumenkov.dspsql;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.sumenkov.dspsql.model.input.JsonInputSearchModel;
import ru.sumenkov.dspsql.model.input.JsonInputStatModel;
import ru.sumenkov.dspsql.model.output.JsonOutputSearchModel;
import ru.sumenkov.dspsql.model.output.JsonOutputStatModel;
import ru.sumenkov.dspsql.repository.StatRepository;
import ru.sumenkov.dspsql.repository.impl.StatRepositoryImpl;
import ru.sumenkov.dspsql.service.SaveJson;
import ru.sumenkov.dspsql.service.SearchService;

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

import java.util.Objects;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println("Error");
        }

        // добавить проверку, файл или нет !!!
        String commandRun = args[0];
        String fileInput = args[1];
        String fileOutput = args[2];

        Object saveObject = new Object();

        try {
            File fileProperties = new File(Objects.requireNonNull(
                    Main.class.getResource("/db.properties")).getFile());
            Properties properties = new Properties();
            properties.load(new FileReader(fileProperties));

            try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties)) {

                switch (commandRun) {
                    case "search":
                    {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonInputSearchModel inputSearchModel = objectMapper.readValue(
                                new File(fileInput),
                                JsonInputSearchModel.class);

                        JsonOutputSearchModel outputSearchModel = new JsonOutputSearchModel();
                        SearchService searchService = new SearchService();

                        outputSearchModel.setResults(searchService.search(conn, inputSearchModel));

                        saveObject = outputSearchModel;
                        break;
                    }
                    case "stat": {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonInputStatModel inputStatModel = objectMapper.readValue(
                                new File(fileInput),
                                JsonInputStatModel.class);

                        String startDate = inputStatModel.getStartDate();
                        String endDate = inputStatModel.getEndDate();

                        JsonOutputStatModel jsonOutputStatModel = new JsonOutputStatModel();
                        jsonOutputStatModel.setTotalDays(getTotalDays(startDate, endDate));

                        StatRepository statRepository = new StatRepositoryImpl(conn, startDate, endDate);
                        jsonOutputStatModel.setCustomers(statRepository.getStatFromDB());

                        saveObject = jsonOutputStatModel;
                    }
                }
            }

            SaveJson.save(fileOutput, saveObject);

        } catch (SQLException | IOException e) {
            new SaveError(e.getMessage());
        }
    }

    private static int getTotalDays(String startDate, String endDate) {
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
            new SaveError(e.getMessage());
        }
        return 0;
    }
}
