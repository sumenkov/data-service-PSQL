package ru.sumenkov.dspsql.model.db;

public class BuyerModel {
    private String firstName;
    private String lastName;

    public BuyerModel() {}

    public BuyerModel(String firstName, String lastName) {
        this.firstName = firstName != null ? firstName: "";
        this.lastName = lastName != null ? lastName: "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        String fullName = !lastName.equals("") || !firstName.equals("") ? lastName + " " + firstName: lastName+firstName;
        return fullName.trim();
    }
}
