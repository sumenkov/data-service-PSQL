package ru.sumenkov.dspsql.model.output;

import ru.sumenkov.dspsql.model.db.BuyersModel;
import ru.sumenkov.dspsql.model.db.ProductsModel;

import java.util.List;

public class StatBuyersOutput {
    private String name;
    private List<ProductsModel> purchases;
    private double totalExpenses;

    public StatBuyersOutput() {}

    public StatBuyersOutput(BuyersModel buyer, List<ProductsModel> products) {
        this.name = buyer.getLastName() + " " + buyer.getFirstName();

        this.purchases = products;

        for(ProductsModel productsModel: purchases) {
            this.totalExpenses += productsModel.getPrice();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductsModel> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<ProductsModel> purchases) {
        this.purchases = purchases;

        for(ProductsModel productsModel: purchases) {
            this.totalExpenses += productsModel.getPrice();
        }
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
