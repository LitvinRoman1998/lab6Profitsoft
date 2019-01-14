package profitsoft.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import profitsoft.data.InsuredPerson;
@Component
public class InsuredPersonJdbcTemplateDao extends DBJdbcTemplateConnection implements IDao<InsuredPerson> {
    private static final String INSURED_PERSON_TABLE_NAME = "insuredperson";
    private static final String INSURED_PERSON_ID = "insuredperson_id";
    private static final String INSURED_PERSON_SURNAME = "surname";
    private static final String INSURED_PERSON_NAME = "name";
    private static final String INSURED_PERSON_PATRONIMIC = "patronimic";
    private static final String INSURED_PERSON_BIRTHDAY = "birthday";
    private static final String INSURED_PERSON_INSURANCE_PRICE = "insurance_price";
    private static final String INSURED_PERSON_TAX_IDENTIFICATION_NUMBER = "tax_identification_number";
    @Bean
    @Override
    public void create(InsuredPerson entity) {
        String insertRequest = "INSERT INTO " + INSURED_PERSON_TABLE_NAME + "(" + INSURED_PERSON_SURNAME + ", " + INSURED_PERSON_NAME + ", " + INSURED_PERSON_PATRONIMIC + ", " + INSURED_PERSON_BIRTHDAY + ", " + INSURED_PERSON_INSURANCE_PRICE + ", " + INSURED_PERSON_TAX_IDENTIFICATION_NUMBER + ") VALUES(?,?,?,?,?,?);";
        jdbcTemplate.update(insertRequest,entity.getLastName(),entity.getFirstName(),entity.getPatronimic(),entity.getBirthDay().plusDays(1),entity.getInsurancePrice(),entity.getTaxIdentificationNumber());
    }
    @Bean
    @Override
    public InsuredPerson read(long id) {

        String selectRequest = "SELECT " + INSURED_PERSON_SURNAME + ", " + INSURED_PERSON_NAME + ", " + INSURED_PERSON_PATRONIMIC + ", " + INSURED_PERSON_BIRTHDAY + ", " + INSURED_PERSON_INSURANCE_PRICE + ", " + INSURED_PERSON_TAX_IDENTIFICATION_NUMBER + " FROM " + INSURED_PERSON_TABLE_NAME + " WHERE " + INSURED_PERSON_ID + "=?;";
        return jdbcTemplate.queryForObject(selectRequest,new Object[]{id}, (resultSet, i) -> {
            InsuredPerson insuredPerson = new InsuredPerson();
            insuredPerson.setLastName(resultSet.getString(INSURED_PERSON_SURNAME));
            insuredPerson.setFirstName(resultSet.getString(INSURED_PERSON_NAME));
            insuredPerson.setPatronimic(resultSet.getString(INSURED_PERSON_PATRONIMIC));
            insuredPerson.setBirthDay(resultSet.getDate(INSURED_PERSON_BIRTHDAY).toLocalDate());
            insuredPerson.setInsurancePrice(resultSet.getInt(INSURED_PERSON_INSURANCE_PRICE));
            insuredPerson.setTaxIdentificationNumber(resultSet.getInt(INSURED_PERSON_TAX_IDENTIFICATION_NUMBER));
            return insuredPerson;
        });
    }
    @Bean
    @Override
    public void update(InsuredPerson entity) {
        String updateRequst = "Update " + INSURED_PERSON_TABLE_NAME + " SET " + INSURED_PERSON_SURNAME + "=?, " + INSURED_PERSON_NAME + "=?, " + INSURED_PERSON_PATRONIMIC + "=?, " + INSURED_PERSON_BIRTHDAY + "=?, " + INSURED_PERSON_INSURANCE_PRICE + "=?, " + INSURED_PERSON_TAX_IDENTIFICATION_NUMBER + "=? WHERE " + INSURED_PERSON_ID + "=?;";
        jdbcTemplate.update(updateRequst,entity.getLastName(),entity.getFirstName(),entity.getPatronimic(),entity.getBirthDay().plusDays(1),entity.getInsurancePrice(),entity.getTaxIdentificationNumber(),entity.getInsuredPersonId());
    }
    @Bean
    @Override
    public void delete(long id) {
        String deleteRequest = "DELETE FROM " + INSURED_PERSON_TABLE_NAME + " WHERE " + INSURED_PERSON_ID + "=?;";
        jdbcTemplate.update(deleteRequest,id);
    }
    @Bean
    public int getLastInsertedId() {
        return super.getLastInsertedId(INSURED_PERSON_TABLE_NAME);
    }
}
