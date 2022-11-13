package ru.sumenkov.dspsql;

import ru.sumenkov.dspsql.model.ErrorModel;
import ru.sumenkov.dspsql.service.SaveJson;

public class SaveError {
    public SaveError(String message) {
        SaveJson.save("Error.json", new ErrorModel(message));
    }
}
