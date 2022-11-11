package ru.sumenkov.dspsql.model.output;

import ru.sumenkov.dspsql.model.input.JsonInputSearchModel;

import java.util.List;

public class JsonOutputSearchModel {
    private final String TYPE = "search";
    private List<Object> results;

    public JsonOutputSearchModel() {
    }

    public JsonOutputSearchModel(List<Object> results) {
        this.results = results;
    }

    public String getTYPE() {
        return TYPE;
    }

    public List<Object> getResults() {
        return results;
    }

    public void setResults(List<Object> results) {
        this.results = results;
    }

    public void setResults(JsonInputSearchModel results) {
        this.results.add(results);
    }

    public void setResults(SearchBuyersOutputModel results) {
        this.results.add(results);
    }

    @Override
    public String toString() {
        return "JsonOutputSearchModel{" +
                "TYPE='" + TYPE + '\'' +
                ", results=" + results +
                '}';
    }
}
