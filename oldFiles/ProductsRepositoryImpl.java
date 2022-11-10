package ru.sumenkov.dspsql.repository.impl;

import ru.sumenkov.dspsql.model.db.BuyersModel;
import ru.sumenkov.dspsql.model.db.ProductsModel;
import ru.sumenkov.dspsql.repository.ProductsRepository;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductsRepositoryImpl implements ProductsRepository {
    private static final Logger log = Logger.getLogger(ProductsRepositoryImpl.class.getName());

    private final Connection conn;
    private final BuyersModel name;

    private static final String QUERY_ID_BUYER = "SELECT ID FROM BUYERS WHERE FIRSTNAME%s and LASTNAME%s";
    private static final String QUERY_PRODUCTS = "SELECT NAME, PRICE FROM PRODUCTS WHERE ID=%d";
    private static final String QUERY_PURCHASES = "SELECT PRODUCTS_ID FROM PURCHASES WHERE BUYERS_ID=%d";

    public ProductsRepositoryImpl(Connection conn, BuyersModel name) {
        this.conn = conn;
        this.name = name;
    }

    @Override
    public List<ProductsModel> getProducts() {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(QUERY_PURCHASES, getIdBuyer()));

            return getProducts(rs);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Fail get products", e);
            return null;
        }
    }

    private int getIdBuyer() throws SQLException {
        int id = 0;

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(QUERY_ID_BUYER,
                    !name.getFirstName().equals("") ? "='" + name.getFirstName() + "'": " IS NULL",
                    !name.getLastName().equals("") ? "='" + name.getLastName() + "'": " IS NULL"));

            while (rs.next()){
                id = rs.getInt("id");
            }
        }
        return id;
    }

    private List<ProductsModel> getProducts(ResultSet inRs) throws SQLException {
        List<ProductsModel> list = new ArrayList<>();
        while (inRs.next()){
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(String.format(QUERY_PRODUCTS, inRs.getInt("products_id")));
                while (rs.next()) {
                    list.add(new ProductsModel(rs.getString("name"), rs.getDouble("price")));
                }
            }
        }
        return list;
    }
}
