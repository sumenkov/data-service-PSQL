package ru.sumenkov.dspsql.model.output;

import ru.sumenkov.dspsql.model.db.BuyersModel;
import ru.sumenkov.dspsql.model.db.ProductsModel;
import ru.sumenkov.dspsql.repository.ProductsRepository;
import ru.sumenkov.dspsql.repository.impl.ProductsRepositoryImpl;

import java.sql.Connection;

public class StatBuyersOutput {
    private String name;
    private ProductsRepository purchases;
    private double totalExpenses;

    public StatBuyersOutput(Connection conn, BuyersModel buyer) {
        this.name = buyer.getLastName() + " " + buyer.getFirstName();
        this.purchases = new ProductsRepositoryImpl(conn, buyer);
        for(ProductsModel productsModel: purchases.getPurchases()) {
            this.totalExpenses += productsModel.getPrice();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductsRepository getPurchases() {
        return purchases;
    }

    public void setPurchases(ProductsRepository purchases) {
        this.purchases = purchases;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    @Override
    public String toString() {
        return "BuyersOutput{" +
                "name='" + name + '\'' +
                ", purchases=" + purchases +
                ", totalExpenses=" + totalExpenses +
                '}';
    }
}
