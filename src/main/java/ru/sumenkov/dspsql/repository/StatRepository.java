package ru.sumenkov.dspsql.repository;

import ru.sumenkov.dspsql.model.output.StatBuyersOutput;

import java.util.List;

public interface StatRepository {
    List<StatBuyersOutput> getStat();
}
