package ru.sumenkov.dspsql.model.input;

import java.util.Date;

public class JsonInputStat {
    private Date startDate;
    private Date endDate;

    public JsonInputStat(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "JsonInputStat{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
