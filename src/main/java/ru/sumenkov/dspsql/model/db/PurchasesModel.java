package ru.sumenkov.dspsql.model.db;

import java.util.Date;

public class PurchasesModel {
    private BuyerModel buyers;
    private ProductModel products;
    private Date dateOfPurchase;

    public PurchasesModel(BuyerModel buyers, ProductModel products, Date dateOfPurchase) {
        this.buyers = buyers;
        this.products = products;
        this.dateOfPurchase = dateOfPurchase;
    }

    public BuyerModel getBuyers() {
        return buyers;
    }

    public void setBuyers(BuyerModel buyers) {
        this.buyers = buyers;
    }

    public ProductModel getProducts() {
        return products;
    }

    public void setProducts(ProductModel products) {
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
