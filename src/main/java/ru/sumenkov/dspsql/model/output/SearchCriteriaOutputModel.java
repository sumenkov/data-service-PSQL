package ru.sumenkov.dspsql.model.output;

import ru.sumenkov.dspsql.model.db.BuyerModel;

import java.util.List;
import java.util.Map;

public class SearchCriteriaOutputModel {
    private Map<String, Object> criteria;
    private List<BuyerModel> results;

    public SearchCriteriaOutputModel() {
    }

    public Map<String, Object> getCriteria() {
        return criteria;
    }

    public void setCriteria(Map<String, Object> criteria) {
        this.criteria = criteria;
    }

    public List<BuyerModel> getResults() {
        return results;
    }

    public void setResults(List<BuyerModel> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "SearchCriteriaOutputModel{" +
                "criteria=" + criteria +
                ", buyers=" + results +
                '}';
    }
}
