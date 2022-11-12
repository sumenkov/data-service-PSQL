package ru.sumenkov.dspsql.model.output;

import ru.sumenkov.dspsql.model.input.JsonInputSearchModel;

import java.util.List;
import java.util.Map;

public class JsonOutputSearchModel {
    private final String TYPE = "search";
    private SearchCriteriaOutputModel results;

    public JsonOutputSearchModel() {
    }

    public JsonOutputSearchModel(SearchCriteriaOutputModel results) {
        this.results = results;
    }

    public String getTYPE() {
        return TYPE;
    }

    public SearchCriteriaOutputModel getResults() {
        return results;
    }

    public void setResults(SearchCriteriaOutputModel results) {
        this.results = results;
    }

    public void setResults(Map<String, Object> criterion) {
        this.results.setCriteria(criterion);
    }

    @Override
    public String toString() {
        return "JsonOutputSearchModel{" +
                "TYPE='" + TYPE + '\'' +
                ", results=" + results +
                '}';
    }
}
