package ru.sumenkov.dspsql.model;

public class ErrorModel {
    private static final String TYPE = "error";
    private String message;

    public ErrorModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorModel{" +
                "TYPE='" + TYPE + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
