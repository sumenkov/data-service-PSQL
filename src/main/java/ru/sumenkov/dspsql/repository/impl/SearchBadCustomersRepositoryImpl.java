package ru.sumenkov.dspsql.repository.impl;

import ru.sumenkov.dspsql.model.db.BuyerModel;
import ru.sumenkov.dspsql.repository.SearchBadCustomersRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchBadCustomersRepositoryImpl implements SearchBadCustomersRepository {
    private static final Logger log = Logger.getLogger(SearchBadCustomersRepositoryImpl.class.getName());

    private final Connection conn;
    private int amount;

    // 4.	Число пассивных покупателей — поиск покупателей, купивших меньше всего товаров. Возвращается не более, чем указанное число покупателей.
    // SELECT COUNT(*), b.firstname, b.lastname FROM buyers b LEFT JOIN purchases pur ON pur.buyer_id = b.id GROUP BY b.firstname, b.lastname ORDER BY COUNT(*) LIMIT 3;
    private static final String QUERY_BAD_CUSTOMERS = "SELECT FIRSTNAME, LASTNAME FROM BUYERS WHERE ID=1 LIMIT=%d";

    public SearchBadCustomersRepositoryImpl(Connection conn, int amount) {
        this.conn = conn;
        this.amount = amount;
    }

    @Override
    public BuyerModel searchBadCustomers() {

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(QUERY_BAD_CUSTOMERS, amount));

            while (rs.next()) {
                System.out.println(rs);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Fail search bad customers", e);
            return null;
        }
        return null;
    }
}
