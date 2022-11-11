package ru.sumenkov.dspsql.model.db;

public class ProductModel {
    private String name;
    private double price;

    public ProductModel(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "ProductsModel{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
