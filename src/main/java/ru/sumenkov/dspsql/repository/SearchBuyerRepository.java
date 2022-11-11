package ru.sumenkov.dspsql.repository;

public interface SearchBuyerRepository {
}

// поиск однофамильцев
// $ SELECT last_name, COUNT(*) as quantity FROM student GROUP BY last_name ORDER BY COUNT(*) DESC;