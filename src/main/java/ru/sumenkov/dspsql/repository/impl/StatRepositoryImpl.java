package ru.sumenkov.dspsql.repository.impl;

import ru.sumenkov.dspsql.SaveError;
import ru.sumenkov.dspsql.model.db.BuyerModel;
import ru.sumenkov.dspsql.model.db.ProductModel;
import ru.sumenkov.dspsql.model.output.StatBuyersOutputModel;
import ru.sumenkov.dspsql.repository.StatRepository;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class StatRepositoryImpl implements StatRepository {
    private final Connection conn;
    private final String startDate, endDate;

    private static final String QUERY_PURCHASES = "SELECT BUYER_ID, PRODUCT_ID FROM PURCHASES WHERE " +
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
                String name = getNameBuyer(rs.getInt("BUYER_ID"));
                int idProduct = rs.getInt("PRODUCT_ID");

                StatBuyersOutputModel buyersOutput = statBuyersOutputList.stream()
                        .filter(statBuyersOutput -> name.equals(statBuyersOutput.getName()))
                        .findAny()
                        .orElse(new StatBuyersOutputModel());

                List<ProductModel> productsList;

                if (buyersOutput.getName() == null) {
                    buyersOutput.setName(name);
                    productsList = getProducts(idProduct);

                    statBuyersOutputList.add(buyersOutput);
                } else {
                    productsList = getProducts(buyersOutput.getPurchases(), idProduct);
                }

                buyersOutput.setPurchases(productsList);

                double tmpTotalExpenses = 0;
                for(ProductModel productsModel: productsList) {
                    tmpTotalExpenses += productsModel.getPrice();
                }

                buyersOutput.setTotalExpenses(tmpTotalExpenses);
            }
        } catch (SQLException e) {
            new SaveError(e.getMessage());
            return null;
        }
        return statBuyersOutputList;
    }

    private String getNameBuyer(int id) throws SQLException {
        String name = "";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format(QUERY_BUYER, id));

            while (rs.next()){
                BuyerModel buyer = new BuyerModel(
                        rs.getString("FIRSTNAME"),
                        rs.getString("LASTNAME"));
                name = buyer.toString();
            }
        }
        return name.trim();
    }

    private List<ProductModel> getProducts(int id) throws SQLException {
        List<ProductModel> productsModelList = new ArrayList<>();
        return getProducts(productsModelList, id);
    }

    private List<ProductModel> getProducts(List<ProductModel> productsModelList, int id) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rsProduct = stmt.executeQuery(String.format(QUERY_PRODUCTS, id));
            while (rsProduct.next()) {
                productsModelList.add(new ProductModel(rsProduct.getString("name"),
                        rsProduct.getDouble("price")));
            }
        }
        return productsModelList;
    }
}
