package ru.sumenkov.dspsql;

import ru.sumenkov.dspsql.model.ErrorModel;
import ru.sumenkov.dspsql.service.SaveJson;

public class SaveException {
    public SaveException(String message) {
        SaveJson.save("./Error.json", new ErrorModel(message));
    }
}
