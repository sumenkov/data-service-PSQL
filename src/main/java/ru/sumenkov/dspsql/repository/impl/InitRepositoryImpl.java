package ru.sumenkov.dspsql.repository.impl;

import ru.sumenkov.dspsql.repository.InitRepository;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitRepositoryImpl implements InitRepository {
    private static final Logger log = Logger.getLogger(InitRepositoryImpl.class.getName());

    private static final String QUERY_INIT_BUYERS =
            "CREATE TABLE BUYERS (ID SERIAL PRIMARY KEY, FIRSTNAME TEXT, LASTNAME TEXT);" +
                    "INSERT INTO BUYERS (FIRSTNAME, LASTNAME) VALUES ('Антон', 'Иванов');" +
                    "INSERT INTO BUYERS (FIRSTNAME, LASTNAME) VALUES ('Иван', 'Николенко');" +
                    "INSERT INTO BUYERS (FIRSTNAME, LASTNAME) VALUES ('Пётр', 'Кривошеев');" +
                    "INSERT INTO BUYERS (FIRSTNAME) VALUES ('Наталья');";
    private static final String QUERY_INIT_PRODUCTS =
            "CREATE TABLE PRODUCTS (ID SERIAL PRIMARY KEY, NAME TEXT, EXPENSES NUMERIC(9, 2));" +
                    "INSERT INTO PRODUCTS (NAME, EXPENSES) VALUES ('Киви', 98.50);" +
                    "INSERT INTO PRODUCTS (NAME, EXPENSES) VALUES ('Яблоко', 135.00);" +
                    "INSERT INTO PRODUCTS (NAME, EXPENSES) VALUES ('Ананас', 320.80);" +
                    "INSERT INTO PRODUCTS (NAME, EXPENSES) VALUES ('Абрикос', 50.30);";
    private static final String QUERY_INIT_PURCHASES =
            "CREATE TABLE PURCHASES (ID SERIAL PRIMARY KEY, BUYERS_ID INTEGER, PRODUCTS_ID INTEGER, " +
                    "FOREIGN KEY (BUYERS_ID) REFERENCES BUYERS(ID), FOREIGN KEY (PRODUCTS_ID) REFERENCES PRODUCTS(ID)," +
                    "DATE_OF_PURCHASE DATE);" +
                    "INSERT INTO PURCHASES (BUYERS_ID, PRODUCTS_ID, DATE_OF_PURCHASE) VALUES (1, 2, '2022-11-01');" +
                    "INSERT INTO PURCHASES (BUYERS_ID, PRODUCTS_ID, DATE_OF_PURCHASE) VALUES (4, 1, '2022-11-05');" +
                    "INSERT INTO PURCHASES (BUYERS_ID, PRODUCTS_ID, DATE_OF_PURCHASE) VALUES (3, 3, '2022-10-21');" +
                    "INSERT INTO PURCHASES (BUYERS_ID, PRODUCTS_ID, DATE_OF_PURCHASE) VALUES (2, 2, '2022-10-21');" +
                    "INSERT INTO PURCHASES (BUYERS_ID, PRODUCTS_ID, DATE_OF_PURCHASE) VALUES (3, 4, '2022-10-21');";
    private final Connection conn;

    public InitRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean initTables() {

        try (Statement stmt = conn.createStatement()) {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, null, null, new String[] {"TABLE"});

            List<String> tables = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("TABLE_NAME");
                tables.add(name);
            }

            if (!tables.contains("buyers")) {
                stmt.execute(QUERY_INIT_BUYERS);
            }

            if (!tables.contains("products")) {
                stmt.execute(QUERY_INIT_PRODUCTS);
            }

            if (!tables.contains("purchases")) {
                stmt.execute(QUERY_INIT_PURCHASES);
            }

            return true;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Fail init tables", e);
            return false;
        }
    }
}
