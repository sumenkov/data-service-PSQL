package ru.sumenkov.dspsql.model.output;

import ru.sumenkov.dspsql.model.db.BuyerModel;

import java.util.List;

public class SearchBuyersOutputModel {
    private List<BuyerModel> results;

    public SearchBuyersOutputModel(List<BuyerModel> results) {
        this.results = results;
    }

    public List<BuyerModel> getResults() {
        return results;
    }

    public void setResults(List<BuyerModel> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "SearchBuyersOutputModel{" +
                "results=" + results +
                '}';
    }
}
