package ru.sumenkov.dspsql.service;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sumenkov.dspsql.model.output.JsonOutputStatModel;

import java.io.File;
import java.io.IOException;

public class SaveStat {

    public static void save(JsonOutputStatModel json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Делаем отступы в 4 пробела (1 таб)
        DefaultPrettyPrinter.Indenter indenter =
                new DefaultIndenter("    ", DefaultIndenter.SYS_LF);
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentObjectsWith(indenter);
        printer.indentArraysWith(indenter);

        objectMapper.writer(printer).writeValue(new File("src/test/output_stat.json"), json);
    }
}
