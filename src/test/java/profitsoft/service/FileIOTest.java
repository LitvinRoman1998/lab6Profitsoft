package profitsoft.service;

import org.junit.Assert;
import org.junit.Test;
import profitsoft.data.Client;
import profitsoft.data.InsuredPerson;
import profitsoft.dict.Type;
import profitsoft.data.Contract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FileIOTest {
    @Test
    public void fileIOTest() {
        FileIS fileIS = new FileIS();
        FileOS fileOS = new FileOS();
        fileIS.setFile("contracts.txt");
        fileOS.setFile("contracts.txt");

        Client clientE = new Client();
        clientE.setType(Type.ENTITY);
        clientE.setCompanyName("abc");
        clientE.setAdress("Pushkin Street, 23");

        Client clientI = new Client();
        clientI.setType(Type.INDIVIDUAL);
        clientI.setLastName("Litvin");
        clientI.setFirstName("Roman");
        clientI.setPatronimic("Mihailovich");
        clientI.setAdress("Pushkinska Street, 23, 18");

        InsuredPerson insuredPerson1 = new InsuredPerson();
        InsuredPerson insuredPerson2 = new InsuredPerson();
        InsuredPerson insuredPerson3 = new InsuredPerson();

        insuredPerson1.setLastName("Litvin");
        insuredPerson1.setFirstName("Roman");
        insuredPerson1.setPatronimic("Mihailovich");
        insuredPerson1.setBirthDay(LocalDate.of(1998, 6, 10));
        insuredPerson1.setInsurancePrice(100000);
        insuredPerson1.setTaxIdentificationNumber(123456789);

        insuredPerson2.setLastName("Milo");
        insuredPerson2.setFirstName("Gaines");
        insuredPerson2.setBirthDay(LocalDate.of(1968, 3, 27));
        insuredPerson2.setInsurancePrice(10000);
        insuredPerson2.setTaxIdentificationNumber(234567891);

        insuredPerson3.setLastName("Dorothy");
        insuredPerson3.setFirstName("Green");
        insuredPerson3.setPatronimic("Alexandrovna");
        insuredPerson3.setBirthDay(LocalDate.of(1979, 6, 1));
        insuredPerson3.setInsurancePrice(22000);
        insuredPerson3.setTaxIdentificationNumber(345678912);

        Set<InsuredPerson> insuredPeople = new LinkedHashSet<>();
        insuredPeople.add(insuredPerson2);
        insuredPeople.add(insuredPerson3);

        Contract contract1=new Contract();
        Contract contract2=new Contract();

        contract1.setId(1);
        contract1.setConclusionDate(LocalDate.of(2010,3,30));
        contract1.setStartDate(LocalDate.of(2010,3,31));
        contract1.setEndDate(LocalDate.of(2020,3,31));
        contract1.setClient(clientI);
        contract1.setInsuredPeople(insuredPerson1);

        contract2.setId(2);
        contract2.setConclusionDate(LocalDate.of(2018,6,21));
        contract2.setStartDate(LocalDate.of(2018,6,22));
        contract2.setEndDate(LocalDate.of(2028,6,22));
        contract2.setClient(clientE);
        contract2.setInsuredPeople(insuredPeople);

        fileOS.uploadToFile(contract1);
        fileOS.uploadToFile(contract2);
        List<Contract> contracts=new ArrayList<>();
        contracts.add(contract1);
        contracts.add(contract2);
        Assert.assertEquals(contracts,fileIS.downloadFromFile());
    }
}
