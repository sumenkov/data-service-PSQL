package ru.sumenkov.dspsql.service;

import ru.sumenkov.dspsql.model.output.JsonOutputStatModel;

public class SaveStat {

    public static void save(JsonOutputStatModel json)  {
        System.out.println(json);
    }
}
