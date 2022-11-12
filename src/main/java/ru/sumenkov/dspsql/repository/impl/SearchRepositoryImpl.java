package ru.sumenkov.dspsql.repository.impl;

import ru.sumenkov.dspsql.model.db.BuyerModel;
import ru.sumenkov.dspsql.repository.SearchRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchRepositoryImpl implements SearchRepository {
    private static final Logger log = Logger.getLogger(SearchRepositoryImpl.class.getName());

    private final Connection conn;

    private static final String QUERY_BUYER = "SELECT FIRSTNAME, LASTNAME FROM BUYERS WHERE LASTNAME='%s'";
    private static final String QUERY_PRODUCT = "";
    private static final String QUERY_EXPENSES = "";
    private static final String QUERY_BAD_CUSTOMERS = "SELECT b.firstname, b.lastname FROM buyers b " +
            "LEFT JOIN purchases pur ON pur.buyer_id = b.id " +
            "GROUP BY b.firstname, b.lastname " +
            "ORDER BY COUNT(*) LIMIT %d";

    public SearchRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<BuyerModel> searchBuyer(String lastname) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(QUERY_BUYER, lastname));
            return setQuery(rs);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Fail search buyer", e);
            return null;
        }
    }

    @Override
    public List<BuyerModel> searchProduct(String name, int amount) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(QUERY_PRODUCT, name, amount));
            return setQuery(rs);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Fail search product", e);
            return null;
        }
    }

    @Override
    public List<BuyerModel> searchExpenses(double min, double max) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(QUERY_EXPENSES, min, max));
            return setQuery(rs);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Fail search expenses", e);
            return null;
        }
    }

    @Override
    public List<BuyerModel> searchBadCustomers(int amount) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(QUERY_BAD_CUSTOMERS, amount));
            return setQuery(rs);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Fail search bad customers", e);
            return null;
        }
    }

    private List<BuyerModel> setQuery(ResultSet rs) throws SQLException {
        List<BuyerModel> buyersList = new ArrayList<>();

        while (rs.next()) {
            BuyerModel buyer = new BuyerModel(
                    rs.getString("FIRSTNAME"),
                    rs.getString("LASTNAME"));
            buyersList.add(buyer);
        }

        return buyersList;
    }
}
