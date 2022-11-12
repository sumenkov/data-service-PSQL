package ru.sumenkov.dspsql.service;

import ru.sumenkov.dspsql.model.input.JsonInputSearchModel;
import ru.sumenkov.dspsql.model.output.JsonOutputSearchModel;

import java.util.Map;

public class SearchService {
    public void search(JsonInputSearchModel inputSearchModel) {
        // criterias=[{lastName=Иванов}, {productName=Ананас, minTimes=2}, {minExpenses=90, maxExpenses=4000}, {badCustomers=3}]
        JsonOutputSearchModel outputSearchModel = new JsonOutputSearchModel();

        for (Map<String, Object> criterion: inputSearchModel.getCriterias()) {
            switch (criterion.keySet().toString()) {
                case "[lastName]":
                    System.out.println("key: lastName");
                    break;
                case "[productName, minTimes]":
                    System.out.println("keys: productName, minTimes");
                    break;
                case "[minExpenses, maxExpenses]":
                    System.out.println("keys: minExpenses, maxExpenses");
                    break;
                case "[badCustomers]":
                    System.out.println("key: badCustomers");
                    break;
                default:
                    System.out.println("Неизвестный запрос");
            }
        }
    }
}
