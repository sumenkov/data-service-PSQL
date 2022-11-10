package ru.sumenkov.dspsql.model.output;

import ru.sumenkov.dspsql.model.db.BuyersModel;
import ru.sumenkov.dspsql.model.db.ProductsModel;

import java.util.List;

public class BuyersOutput {
    private BuyersModel name;
    private List<ProductsModel> purchases;
    private double totalExpenses;

    public BuyersOutput(BuyersModel name) {
        this.name = name;

        this.purchases.add(new ProductsModel("", 0)); // собрать все покупки

        for(ProductsModel productsModel: purchases) {
            this.totalExpenses += productsModel.getExpenses();
        }
    }

    public BuyersModel getName() {
        return name;
    }

    public void setName(BuyersModel name) {
        this.name = name;
    }

    public List<ProductsModel> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<ProductsModel> purchases) {
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
