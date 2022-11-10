package ru.sumenkov.dspsql.model.output;

import ru.sumenkov.dspsql.model.db.ProductsModel;

import java.util.List;

public class StatBuyersOutputModel {
    private String name;
    private List<ProductsModel> purchases;
    private double totalExpenses;

    public StatBuyersOutputModel() {}

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
        this.totalExpenses = 0;

        for(ProductsModel productsModel: purchases) {
            this.totalExpenses += productsModel.getPrice();
        }
    }

    public double getTotalExpenses() {
        return totalExpenses;
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
