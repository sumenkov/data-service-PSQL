package ru.sumenkov.dspsql.model.output;

import java.util.List;

public class JsonOutputStatModel {
    private static final String TYPE = "stat";
    private int totalDays;
    private List<StatBuyersOutputModel> customers;
    private double totalExpenses;
    private double avgExpenses;

    public JsonOutputStatModel() {}

    public String getType() {
        return TYPE;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public List<StatBuyersOutputModel> getCustomers() {
        return customers;
    }

    public void setCustomers(List<StatBuyersOutputModel> customers) {
        this.customers = customers;
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
}
