package ru.sumenkov.dspsql.model.input;

import java.util.List;

public class JsonInputSearchModel {
    private List<Object> criterias;

    public JsonInputSearchModel(){

    }

    public List<Object> getCriterias() {
        return criterias;
    }

    @Override
    public String toString() {
        return "JsonInputSearchModel{" +
                "criterias=" + criterias +
                '}';
    }
}
