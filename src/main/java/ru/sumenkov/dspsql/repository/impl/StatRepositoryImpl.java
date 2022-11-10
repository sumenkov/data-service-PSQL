package ru.sumenkov.dspsql.repository.impl;

import ru.sumenkov.dspsql.model.db.ProductsModel;
import ru.sumenkov.dspsql.model.output.StatBuyersOutputModel;
import ru.sumenkov.dspsql.repository.StatRepository;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatRepositoryImpl implements StatRepository {
    private static final Logger log = Logger.getLogger(StatRepositoryImpl.class.getName());
    private final Connection conn;
    private final String startDate, endDate;

    private static final String QUERY_PURCHASES = "SELECT BUYERS_ID, PRODUCTS_ID FROM PURCHASES WHERE " +
            "DATE_OF_PURCHASE >= '%s' AND DATE_OF_PURCHASE <= '%s'";
    private static final String QUERY_BUYER = "SELECT FIRSTNAME, LASTNAME FROM BUYERS WHERE ID=%d";
    private static final String QUERY_PRODUCTS = "SELECT NAME, PRICE FROM PRODUCTS WHERE ID=%d";

    public StatRepositoryImpl(Connection conn, String startDate, String endDate) {
        this.conn = conn;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public List<StatBuyersOutputModel> getStatFromDB() {
        List<StatBuyersOutputModel> statBuyersOutputList = new ArrayList<>();

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(QUERY_PURCHASES, startDate, endDate));

            while (rs.next()){
                String name = getNameBuyer(rs.getInt("BUYERS_ID"));
                int idProduct = rs.getInt("PRODUCTS_ID");

                StatBuyersOutputModel buyersOutput = statBuyersOutputList.stream()
                        .filter(statBuyersOutput -> name.equals(statBuyersOutput.getName()))
                        .findAny()
                        .orElse(new StatBuyersOutputModel());

                if (buyersOutput.getName() == null) {
                    buyersOutput.setName(name);
                    buyersOutput.setPurchases(getProducts(idProduct));

                    statBuyersOutputList.add(buyersOutput);
                } else {
                    buyersOutput.setPurchases(getProducts(buyersOutput.getPurchases(), idProduct));
                }
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, "Fail get stat", e);
            return null;
        }
        return statBuyersOutputList;
    }

    private String getNameBuyer(int id) throws SQLException {
        String name = "";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(QUERY_BUYER, id));

            while (rs.next()){
                String lastName = rs.getString("LASTNAME") != null ? rs.getString("LASTNAME"): "";
                String firstName = rs.getString("FIRSTNAME") != null ? rs.getString("FIRSTNAME"): "";
                name = !lastName.equals("") || !firstName.equals("") ? lastName + " " + firstName: lastName+firstName;
            }
        }
        return name.trim();
    }

    private List<ProductsModel> getProducts(int id) throws SQLException {
        List<ProductsModel> productsModelList = new ArrayList<>();
        return getProducts(productsModelList, id);
    }

    private List<ProductsModel> getProducts(List<ProductsModel> productsModelList, int id) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rsProduct = stmt.executeQuery(String.format(QUERY_PRODUCTS, id));
            while (rsProduct.next()) {
                productsModelList.add(new ProductsModel(rsProduct.getString("name"),
                        rsProduct.getDouble("price")));
            }
        }
        return productsModelList;
    }
}
