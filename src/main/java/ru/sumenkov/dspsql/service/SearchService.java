package ru.sumenkov.dspsql.service;

import ru.sumenkov.dspsql.model.input.JsonInputSearchModel;
import ru.sumenkov.dspsql.model.output.SearchCriteriaOutputModel;
import ru.sumenkov.dspsql.repository.SearchRepository;
import ru.sumenkov.dspsql.repository.impl.SearchRepositoryImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchService {

    public List<SearchCriteriaOutputModel> search(Connection conn, JsonInputSearchModel inputSearchModel) {

        List<SearchCriteriaOutputModel> criteriaOutputList = new ArrayList<>();
        SearchRepository searchRepository = new SearchRepositoryImpl(conn);

        for (Map<String, Object> criterion: inputSearchModel.getCriterias()) {
            SearchCriteriaOutputModel criteriaOutput = new SearchCriteriaOutputModel();
            criteriaOutput.setCriteria(criterion);

            switch (criterion.keySet().toString()) {
                case "[lastName]":
                    criteriaOutput.setResults(
                            searchRepository.searchBuyer(
                                    (String) criterion.get("lastName")
                            )
                    );
                    break;
                case "[productName, minTimes]":
                    criteriaOutput.setResults(
                            searchRepository.searchProduct(
                                    (String) criterion.get("productName"),
                                    (Integer) criterion.get("minTimes")
                            )
                    );
                    break;
                case "[minExpenses, maxExpenses]":
                    criteriaOutput.setResults(
                            searchRepository.searchExpenses(
                                    (Integer) criterion.get("minExpenses"),
                                    (Integer) criterion.get("maxExpenses")
                            )
                    );
                    break;
                case "[badCustomers]":
                    criteriaOutput.setResults(
                            searchRepository.searchBadCustomers(
                                    (Integer) criterion.get("badCustomers")
                            )
                    );
                    break;
                default:
                    System.out.println("Неизвестный запрос");
            }
            criteriaOutputList.add(criteriaOutput);
        }
        return criteriaOutputList;
    }
}
