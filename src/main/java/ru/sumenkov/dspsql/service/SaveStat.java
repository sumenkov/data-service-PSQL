package ru.sumenkov.dspsql.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sumenkov.dspsql.model.output.JsonOutputStatModel;

import java.io.File;
import java.io.IOException;

public class SaveStat {

    public static void save(JsonOutputStatModel json) throws IOException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("src/test/output_stat.json"), json);
    }
}
