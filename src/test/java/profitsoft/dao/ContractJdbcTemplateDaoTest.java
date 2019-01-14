package profitsoft.dao;

import org.junit.Assert;
import org.junit.Test;
import profitsoft.data.Client;
import profitsoft.data.Contract;
import profitsoft.data.InsuredPerson;
import profitsoft.dict.Type;

import java.time.LocalDate;

public class ContractJdbcTemplateDaoTest {
    @Test
    public void readTest() {
        Contract contract1=new Contract();
        Contract contract2=new Contract();

        InsuredPerson insuredPerson1 = new InsuredPerson();
        InsuredPerson insuredPerson2 = new InsuredPerson();

        insuredPerson1.setLastName("Dorothy");
        insuredPerson1.setFirstName("Green");
        insuredPerson1.setPatronimic("Alexandrovna");
        insuredPerson1.setBirthDay(LocalDate.of(1979, 6, 1));
        insuredPerson1.setInsurancePrice(22000);
        insuredPerson1.setTaxIdentificationNumber(345678912);

        insuredPerson2.setLastName("Black");
        insuredPerson2.setFirstName("Dan");
        insuredPerson2.setBirthDay(LocalDate.of(1981, 9, 12));
        insuredPerson2.setInsurancePrice(35000);
        insuredPerson2.setTaxIdentificationNumber(456789123);

        Client client =new Client();
        client.setType(Type.ENTITY);
        client.setCompanyName("NBC OOA");
        client.setAdress("Universitetska, 26, 12");

        contract1.setConclusionDate(LocalDate.of(2010,3,20));
        contract1.setStartDate(LocalDate.of(2010,3,21));
        contract1.setEndDate(LocalDate.of(2020,3,21));
        contract1.setClient(client);
        contract1.setInsuredPeople(insuredPerson1,insuredPerson2);

        ContractJdbcTemplateDao contractJdbcTemplateDao =new ContractJdbcTemplateDao();
        contractJdbcTemplateDao.create(contract1);
        contract2= contractJdbcTemplateDao.read(ContractJdbcTemplateDao.lastInsertedContract);
        Assert.assertEquals(contract1,contract2);
    }
}
