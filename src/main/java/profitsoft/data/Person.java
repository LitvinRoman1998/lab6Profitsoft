package profitsoft.data;

public class Person {
    String lastName;
    String firstName;
    String patronimic;

    public String getPerson() {
        return this.lastName + ";" + this.firstName + ";" + ((this.patronimic != null) ? this.patronimic : " ");
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronimic() {
        return patronimic;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPatronimic(String patronimic) {
        this.patronimic = patronimic;
    }
}
