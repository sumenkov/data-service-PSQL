package ru.sumenkov.dspsql;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.cli.*;

import ru.sumenkov.dspsql.model.ErrorModel;
import ru.sumenkov.dspsql.model.input.JsonInputSearchModel;
import ru.sumenkov.dspsql.model.input.JsonInputStatModel;
import ru.sumenkov.dspsql.model.output.JsonOutputSearchModel;
import ru.sumenkov.dspsql.model.output.JsonOutputStatModel;
import ru.sumenkov.dspsql.repository.StatRepository;
import ru.sumenkov.dspsql.repository.impl.InitRepositoryImpl;
import ru.sumenkov.dspsql.repository.impl.StatRepositoryImpl;
import ru.sumenkov.dspsql.service.SaveJson;
import ru.sumenkov.dspsql.service.SearchService;

import java.io.File;
import java.io.FileNotFoundException;
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

        CommandLineParser commandLineParser = new DefaultParser();
        Options options = new LaunchOptions().launchOptions();

        if (args.length == 0) helper(options);

        CommandLine commandLine;
        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String[] arguments = commandLine.getArgs();
        String fileInput = arguments[0];
        String fileOutput = arguments[1];

        Object saveObject = new Object();

        try {
            File fileProperties = new File("src/main/resources/db.properties");
            Properties properties = new Properties();

            properties.load(new FileReader(fileProperties));

            try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties)) {
                boolean init = new InitRepositoryImpl(conn).initTables();
                if (!init) {
                    log.info("App stopped: fail init");
                }

                if (commandLine.hasOption("s")) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonInputSearchModel inputSearchModel = objectMapper.readValue(
                            new File(fileInput),
                            JsonInputSearchModel.class);

                    JsonOutputSearchModel outputSearchModel = new JsonOutputSearchModel();
                    SearchService searchService = new SearchService();

                    outputSearchModel.setResults(searchService.search(conn, inputSearchModel));

                    saveObject = outputSearchModel;

                } else if (commandLine.hasOption("st")) {
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

            SaveJson.save(fileOutput, saveObject);

        } catch (SQLException e) {
            log.log(Level.SEVERE, "Fail open connect", e);
            try {
                SaveJson.save(fileOutput, new ErrorModel("Fail open connect"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (FileNotFoundException e) {
            log.log(Level.SEVERE, "Fail, file not found", e);
        } catch (StreamReadException e) {
            log.log(Level.SEVERE, "Fail Stream read", e);
        } catch (DatabindException e) {
            log.log(Level.SEVERE, "Fail Data bind", e);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Fail save file", e);
        }
    }

    private static void helper(Options options){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("data-service-PSQL", options, true);
        System.exit(0);
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
