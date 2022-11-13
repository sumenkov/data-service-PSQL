package ru.sumenkov.dspsql;

import ru.sumenkov.dspsql.model.ErrorModel;
import ru.sumenkov.dspsql.service.SaveJson;

public class SaveError {
    public SaveError(String fileOutput, String message) {
        SaveJson.save(fileOutput, new ErrorModel(message));
    }
}
