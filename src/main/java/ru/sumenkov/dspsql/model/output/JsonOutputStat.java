package ru.sumenkov.dspsql.model.output;

import java.util.List;

public class JsonOutputStat {
    private String TYPE = "stat";
    private int totalDays;
    private List<StatBuyersOutput> customers;
}
