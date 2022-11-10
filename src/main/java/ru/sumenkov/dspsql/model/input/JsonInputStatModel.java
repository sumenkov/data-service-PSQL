package ru.sumenkov.dspsql.model.input;

public class JsonInputStatModel {
    private String startDate;
    private String endDate;

    public JsonInputStatModel() {}

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "JsonInputStatModel{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
