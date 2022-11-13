package ru.sumenkov.dspsql.model.output;

import java.util.List;

public class JsonOutputSearchModel {
    private static final String TYPE = "search";
    private List<SearchCriteriaOutputModel> results;

    public JsonOutputSearchModel() {
    }

    public String getTYPE() {
        return TYPE;
    }

    public List<SearchCriteriaOutputModel> getResults() {
        return results;
    }

    public void setResults(List<SearchCriteriaOutputModel> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "JsonOutputSearchModel{" +
                "TYPE='" + TYPE + '\'' +
                ", results=" + results +
                '}';
    }
}
