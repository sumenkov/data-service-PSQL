package ru.sumenkov.dspsql.model.output;

import ru.sumenkov.dspsql.model.db.ProductsModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class JsonOutputStatModel {
    private String TYPE = "stat";

    private long totalDays;
    private List<StatBuyersOutputModel> customers;

    private double totalExpenses;
    private double avgExpenses;

    public JsonOutputStatModel() {}

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public long getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(long totalDays) {
        this.totalDays = totalDays;
    }

    public List<StatBuyersOutputModel> getCustomers() {
        return customers;
    }

    public void setCustomers(List<StatBuyersOutputModel> customers) {
        this.customers = customers;
        this.totalExpenses = 0;
        this.avgExpenses = 0;
        int allBuyers = 0;

        for (StatBuyersOutputModel statBuyer: customers) {
            allBuyers++;
            this.totalExpenses += statBuyer.getTotalExpenses();
//            for (ProductsModel product: statBuyer.getPurchases()) {
//                this.totalExpenses += product.getPrice();
//            }
        }

        this.avgExpenses = round(totalExpenses / allBuyers);
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getAvgExpenses() {
        return avgExpenses;
    }

    public void setAvgExpenses(double avgExpenses) {
        this.avgExpenses = avgExpenses;
    }

    @Override
    public String toString() {
        return "JsonOutputStatModel{" +
                "TYPE='" + TYPE + '\'' +
                ", totalDays=" + totalDays +
                ", customers=" + customers +
                ", totalExpenses=" + totalExpenses +
                ", avgExpenses=" + avgExpenses +
                '}';
    }

    private double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
