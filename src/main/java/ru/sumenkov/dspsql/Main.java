package ru.sumenkov.dspsql;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.sumenkov.dspsql.model.input.JsonInputSearchModel;
import ru.sumenkov.dspsql.model.input.JsonInputStatModel;
import ru.sumenkov.dspsql.model.output.JsonOutputSearchModel;
import ru.sumenkov.dspsql.model.output.JsonOutputStatModel;
import ru.sumenkov.dspsql.model.output.StatBuyersOutputModel;
import ru.sumenkov.dspsql.repository.StatRepository;
import ru.sumenkov.dspsql.repository.impl.StatRepositoryImpl;
import ru.sumenkov.dspsql.service.SaveJson;
import ru.sumenkov.dspsql.service.SearchService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        if (args.length != 3) {
            new SaveError("Не верное количество аргументов запуска");
        }

        // добавить проверку, файл или нет !!!
        String commandRun = args[0];
        String fileInput = args[1];
        String fileOutput = args[2];

        Object saveObject = new Object();

        try {
            ObjectMapper configObjectMapper = new ObjectMapper();
            JsonNode jsonNode = configObjectMapper.readTree(new FileReader("config.json"));
            try (Connection conn = DriverManager.getConnection(
                    jsonNode.get("url").asText(),
                    jsonNode.get("user").asText(),
                    jsonNode.get("password").asText()))
            {
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
                        List<StatBuyersOutputModel> statBuyersList = statRepository.getStatFromDB();
                        jsonOutputStatModel.setCustomers(statBuyersList);

                        for (StatBuyersOutputModel statBuyer: statBuyersList) {
                            jsonOutputStatModel.setTotalExpenses(
                                    jsonOutputStatModel.getTotalExpenses()
                                            + statBuyer.getTotalExpenses());
                        }

                        jsonOutputStatModel.setAvgExpenses(
                                round(
                                        jsonOutputStatModel.getTotalExpenses()
                                                / jsonOutputStatModel.getCustomers().size()));

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

    private static double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
