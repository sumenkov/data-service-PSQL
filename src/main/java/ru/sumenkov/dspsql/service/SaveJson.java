package ru.sumenkov.dspsql.service;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sumenkov.dspsql.Main;
import ru.sumenkov.dspsql.SaveError;

import java.io.File;
import java.io.IOException;

public class SaveJson {

    public static void save(String fileOutput, Object json) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Делаем отступы в 4 пробела (1 таб)
        DefaultPrettyPrinter.Indenter indenter =
                new DefaultIndenter("    ", DefaultIndenter.SYS_LF);
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentObjectsWith(indenter);
        printer.indentArraysWith(indenter);

        try {
            objectMapper.writer(printer).writeValue(new File(fileOutput), json);
        } catch (IOException e) {
            new SaveError(Main.fileOutput, e.getMessage());
        }
    }
}
