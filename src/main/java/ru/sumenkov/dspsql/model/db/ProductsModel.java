package ru.sumenkov.dspsql.model.db;

public class ProductsModel {
    private String name;
    private double expenses;

    public ProductsModel(String name, double expenses) {
        this.name = name;
        this.expenses = expenses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    @Override
    public String toString() {
        return "ProductsModel{" +
                "name='" + name + '\'' +
                ", price=" + expenses +
                '}';
    }
}
