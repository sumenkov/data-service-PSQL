package ru.sumenkov.dspsql.service;

import ru.sumenkov.dspsql.Main;
import ru.sumenkov.dspsql.SaveError;
import ru.sumenkov.dspsql.model.db.BuyerModel;
import ru.sumenkov.dspsql.model.input.JsonInputSearchModel;
import ru.sumenkov.dspsql.model.output.SearchCriteriaOutputModel;
import ru.sumenkov.dspsql.repository.SearchRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SearchService {
    SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public List<SearchCriteriaOutputModel> search(JsonInputSearchModel inputSearchModel) {
        List<SearchCriteriaOutputModel> criteriaOutputList = new ArrayList<>();
        for (Map<String, Object> criterion: inputSearchModel.getCriterias()) {
            SearchCriteriaOutputModel criteriaOutput = new SearchCriteriaOutputModel();
            criteriaOutput.setCriteria(criterion);
            criteriaOutput.setResults(getSearchRepository(criterion));
            criteriaOutputList.add(criteriaOutput);
        }
        return criteriaOutputList;
    }

    private List<BuyerModel> getSearchRepository(Map<String, Object> criterion) {
        switch (criterion.keySet().toString()) {
            case "[lastName]":
                return searchRepository.searchBuyer((String) criterion.get("lastName"));
            case "[productName, minTimes]":
                return searchRepository.searchProduct(
                                (String) criterion.get("productName"),
                                (Integer) criterion.get("minTimes"));
            case "[minExpenses, maxExpenses]":
                return searchRepository.searchExpenses(
                                (Integer) criterion.get("minExpenses"),
                                (Integer) criterion.get("maxExpenses"));
            case "[badCustomers]":
                return searchRepository.searchBadCustomers(
                                (Integer) criterion.get("badCustomers"));
            default:
                new SaveError(Main.fileOutput, "Неизвестный запрос");
        }
        return Collections.emptyList();
    }
}
