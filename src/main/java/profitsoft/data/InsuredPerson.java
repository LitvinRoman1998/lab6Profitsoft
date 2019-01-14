package profitsoft.data;


import java.time.LocalDate;


public class InsuredPerson extends Person {
    int insuredPersonId;
    LocalDate birthDay;
    int insurancePrice;
    int taxIdentificationNumber;

    public String getInsuredPerson() {
        return getPerson() + ";" + this.birthDay + ";" + this.insurancePrice + ";" + this.taxIdentificationNumber;
    }

    public String printInsuredPerson() {
        return lastName + " " + firstName.charAt(0) + ". " + (((patronimic != null) && !(this.patronimic.trim().isEmpty())) ? patronimic.charAt(0) + "." : "");
    }

    @Override
    public int hashCode() {
        return taxIdentificationNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof InsuredPerson)) {
            return false;
        }
        return this.taxIdentificationNumber == ((InsuredPerson) obj).taxIdentificationNumber;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public int getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(int insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public int getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(int taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public int getInsuredPersonId() {
        return insuredPersonId;
    }

    public void setInsuredPersonId(int insuredPersonId) {
        this.insuredPersonId = insuredPersonId;
    }
}
