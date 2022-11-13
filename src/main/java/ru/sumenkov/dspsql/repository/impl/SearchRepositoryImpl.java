package ru.sumenkov.dspsql.repository.impl;

import ru.sumenkov.dspsql.GetQuery;
import ru.sumenkov.dspsql.Main;
import ru.sumenkov.dspsql.SaveError;
import ru.sumenkov.dspsql.model.db.BuyerModel;
import ru.sumenkov.dspsql.repository.SearchRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SearchRepositoryImpl implements SearchRepository {
    private final Connection conn;

    public SearchRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<BuyerModel> searchBuyer(String lastname) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(GetQuery.get("QUERY_BUYER_LASTNAME"), lastname));
            return setQuery(rs);
        } catch (SQLException e) {
            new SaveError(Main.fileOutput, e.getMessage());
            return null;
        }
    }

    @Override
    public List<BuyerModel> searchProduct(String name, int amount) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(GetQuery.get("QUERY_PRODUCT"), name, amount));
            return setQuery(rs);
        } catch (SQLException e) {
            new SaveError(Main.fileOutput, e.getMessage());
            return null;
        }
    }

    @Override
    public List<BuyerModel> searchExpenses(int min, int max) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(GetQuery.get("QUERY_EXPENSES"), min, max));
            return setQuery(rs);
        } catch (SQLException e) {
            new SaveError(Main.fileOutput, e.getMessage());
            return null;
        }
    }

    @Override
    public List<BuyerModel> searchBadCustomers(int amount) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(GetQuery.get("QUERY_BAD_CUSTOMERS"), amount));
            return setQuery(rs);
        } catch (SQLException e) {
            new SaveError(Main.fileOutput, e.getMessage());
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
