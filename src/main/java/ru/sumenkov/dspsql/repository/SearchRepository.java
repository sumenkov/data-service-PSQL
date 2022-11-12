package ru.sumenkov.dspsql.repository;

import ru.sumenkov.dspsql.model.db.BuyerModel;

import java.util.List;

public interface SearchRepository {
    List<BuyerModel> searchBuyer(String lastname);
    List<BuyerModel> searchProduct(String name, int amount);
    List<BuyerModel> searchExpenses(double min, double max);
    List<BuyerModel> searchBadCustomers(int amount);
}
