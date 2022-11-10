package ru.sumenkov.dspsql.repository;

import ru.sumenkov.dspsql.model.output.StatBuyersOutputModel;

import java.util.List;

public interface StatRepository {
    List<StatBuyersOutputModel> getStatFromDB();
}
