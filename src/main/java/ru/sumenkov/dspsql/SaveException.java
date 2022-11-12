package ru.sumenkov.dspsql;

import ru.sumenkov.dspsql.model.ErrorModel;
import ru.sumenkov.dspsql.service.SaveJson;

public class SaveException {
    private final String message;

    public SaveException(String message) {
        this.message = message;

        SaveJson.save("Error.json", new ErrorModel(message));
    }

    public String getMessage() {
        return message;
    }
}
