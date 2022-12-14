package ru.sumenkov.dspsql.model.output;

import ru.sumenkov.dspsql.model.db.ProductModel;

import java.util.List;

public class StatBuyersOutputModel {
    private String name;
    private List<ProductModel> purchases;
    private double totalExpenses;

    public StatBuyersOutputModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductModel> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<ProductModel> purchases) {
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
