package profitsoft.dao;

import org.junit.Assert;
import org.junit.Test;
import profitsoft.data.InsuredPerson;

import java.time.LocalDate;

public class InsuredPersonJdbcTemplateDaoTest {
    @Test
    public void readTest(){
        InsuredPerson insuredPerson1 = new InsuredPerson();
        InsuredPerson insuredPerson2 = new InsuredPerson();

        insuredPerson1.setLastName("Litvin");
        insuredPerson1.setFirstName("Roman");
        insuredPerson1.setPatronimic("Mihailovich");
        insuredPerson1.setBirthDay(LocalDate.of(1998, 6, 10));
        insuredPerson1.setInsurancePrice(100000);
        insuredPerson1.setTaxIdentificationNumber(123456789);
        InsuredPersonJdbcTemplateDao insuredPersonJdbcTemplateDao =new InsuredPersonJdbcTemplateDao();
        insuredPersonJdbcTemplateDao.create(insuredPerson1);
        int id= insuredPersonJdbcTemplateDao.getLastInsertedId();
        insuredPerson2= insuredPersonJdbcTemplateDao.read(id);
        Assert.assertEquals(insuredPerson1,insuredPerson2);
    }

    @Test
    public void updateTest(){
        InsuredPerson insuredPerson1 = new InsuredPerson();
        InsuredPerson insuredPerson2 = new InsuredPerson();
        InsuredPersonJdbcTemplateDao insuredPersonJdbcTemplateDao =new InsuredPersonJdbcTemplateDao();

        insuredPerson2.setLastName("Milo");
        insuredPerson2.setFirstName("Gaines");
        insuredPerson2.setBirthDay(LocalDate.of(1968, 3, 27));
        insuredPerson2.setInsurancePrice(10000);
        insuredPerson2.setTaxIdentificationNumber(234567891);

        insuredPersonJdbcTemplateDao.create(insuredPerson2);
        int id= insuredPersonJdbcTemplateDao.getLastInsertedId();
        insuredPerson2.setInsuredPersonId(id);
        insuredPerson2.setInsurancePrice(100000);
        insuredPersonJdbcTemplateDao.update(insuredPerson2);
        insuredPerson1= insuredPersonJdbcTemplateDao.read(id);
        Assert.assertEquals(insuredPerson2,insuredPerson1);
    }
}
