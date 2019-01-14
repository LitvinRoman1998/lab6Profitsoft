package profitsoft.data;

import java.time.LocalDate;
import java.util.*;

public class Contract {
    int id;
    LocalDate conclusionDate;
    LocalDate startDate;
    LocalDate endDate;
    Client client;
    Set<InsuredPerson> insuredPeople;

    public int totalInsuranceCost() {
        int contractCost = 0;
        for (InsuredPerson insuredPerson : insuredPeople) {
            contractCost += insuredPerson.getInsurancePrice();
        }
        return contractCost;
    }

    public InsuredPerson findByTaxIdentificationNumber(int taxIdentificationNumber) {
        for (InsuredPerson insuredperson : insuredPeople) {
            if (taxIdentificationNumber == insuredperson.getTaxIdentificationNumber()) {
                return insuredperson;
            }
        }
        return null;
    }

    public void sortByAlphabet() {
        List<InsuredPerson> insuredPersonList = new ArrayList<>();
        insuredPersonList.addAll(insuredPeople);
        insuredPersonList.sort(Comparator.comparing(Person::getLastName));
        this.insuredPeople.addAll(insuredPersonList);
    }


    public void sortByBirthDate() {
        List<InsuredPerson> insuredPersonList = new ArrayList<>();
        insuredPersonList.addAll(insuredPeople);
        insuredPersonList.sort(Comparator.comparing(InsuredPerson::getBirthDay));
        this.insuredPeople.addAll(insuredPersonList);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "number: " + id +
                ", conclusionDate: " + conclusionDate +
                ", startDate: " + startDate +
                ", endDate: " + endDate +
                ", client: " + client +
                ", insuredPeople: " + insuredPeople;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return id == contract.id &&
                Objects.equals(conclusionDate, contract.conclusionDate) &&
                Objects.equals(startDate, contract.startDate) &&
                Objects.equals(endDate, contract.endDate) &&
                Objects.equals(client, contract.client) &&
                Objects.equals(insuredPeople, contract.insuredPeople);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, conclusionDate, startDate, endDate, client, insuredPeople);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getConclusionDate() {
        return conclusionDate;
    }

    public void setConclusionDate(LocalDate conclusionDate) {
        this.conclusionDate = conclusionDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<InsuredPerson> getInsuredPeople() {
        return insuredPeople;
    }

    public void setInsuredPeople(Set<InsuredPerson> insuredPeople) {
        this.insuredPeople = insuredPeople;
    }

    public void setInsuredPeople(InsuredPerson... insuredPeople){
        this.insuredPeople=new LinkedHashSet<>();
        this.insuredPeople.addAll(Arrays.asList(insuredPeople));
    }
}
