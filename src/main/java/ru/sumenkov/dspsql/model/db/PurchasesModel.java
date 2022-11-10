package ru.sumenkov.dspsql.model.db;

import java.util.Date;

public class PurchasesModel {
    private BuyersModel buyers;
    private ProductsModel products;
    private Date dateOfPurchase;

    public PurchasesModel(BuyersModel buyers, ProductsModel products, Date dateOfPurchase) {
        this.buyers = buyers;
        this.products = products;
        this.dateOfPurchase = dateOfPurchase;
    }

    public BuyersModel getBuyers() {
        return buyers;
    }

    public void setBuyers(BuyersModel buyers) {
        this.buyers = buyers;
    }

    public ProductsModel getProducts() {
        return products;
    }

    public void setProducts(ProductsModel products) {
        this.products = products;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    @Override
    public String toString() {
        return "PurchasesModel{" +
                "buyers=" + buyers +
                ", products=" + products +
                ", dateOfPurchase=" + dateOfPurchase +
                '}';
    }
}
