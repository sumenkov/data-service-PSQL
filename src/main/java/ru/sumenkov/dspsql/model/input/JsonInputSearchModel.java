package ru.sumenkov.dspsql.model.input;

import java.util.List;
import java.util.Map;

public class JsonInputSearchModel {
    private List<Map<String, Object>> criterias;

    public JsonInputSearchModel(){
    }

    public List<Map<String, Object>> getCriterias() {
        return criterias;
    }

    @Override
    public String toString() {
        return "JsonInputSearchModel{" +
                "criterias=" + criterias +
                '}';
    }
}
