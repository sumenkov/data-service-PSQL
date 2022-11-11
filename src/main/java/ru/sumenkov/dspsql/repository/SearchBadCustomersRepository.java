package ru.sumenkov.dspsql.repository;

import ru.sumenkov.dspsql.model.db.BuyerModel;

public interface SearchBadCustomersRepository {
    BuyerModel searchBadCustomers();
}
