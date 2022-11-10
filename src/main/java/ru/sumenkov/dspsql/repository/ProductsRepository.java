package ru.sumenkov.dspsql.repository;

import ru.sumenkov.dspsql.model.db.ProductsModel;

import java.util.List;

public interface ProductsRepository {
    List<ProductsModel> getPurchases();
}
