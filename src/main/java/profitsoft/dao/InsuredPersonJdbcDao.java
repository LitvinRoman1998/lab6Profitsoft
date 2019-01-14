package profitsoft.dao;

import profitsoft.connection.ConnectionFactory;
import profitsoft.data.InsuredPerson;

import java.sql.*;

public class InsuredPersonJdbcDao extends DBJdbcConnection implements IDao<InsuredPerson> {
    private static final String INSURED_PERSON_TABLE_NAME = "insuredperson";
    private static final String INSURED_PERSON_ID = "insuredperson_id";
    private static final String INSURED_PERSON_SURNAME = "surname";
    private static final String INSURED_PERSON_NAME = "name";
    private static final String INSURED_PERSON_PATRONIMIC = "patronimic";
    private static final String INSURED_PERSON_BIRTHDAY = "birthday";
    private static final String INSURED_PERSON_INSURANCE_PRICE = "insurance_price";
    private static final String INSURED_PERSON_TAX_IDENTIFICATION_NUMBER = "tax_identification_number";

    @Override
    public void create(InsuredPerson entity) {
        String insertRequest = "INSERT INTO " + INSURED_PERSON_TABLE_NAME + "(" + INSURED_PERSON_SURNAME + ", " + INSURED_PERSON_NAME + ", " + INSURED_PERSON_PATRONIMIC + ", " + INSURED_PERSON_BIRTHDAY + ", " + INSURED_PERSON_INSURANCE_PRICE + ", " + INSURED_PERSON_TAX_IDENTIFICATION_NUMBER + ") VALUES(?,?,?,?,?,?);";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertRequest)) {
            preparedStatement.setString(1, entity.getLastName());
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getPatronimic());
            preparedStatement.setDate(4, Date.valueOf(entity.getBirthDay().plusDays(1)));
            preparedStatement.setInt(5, entity.getInsurancePrice());
            preparedStatement.setInt(6, entity.getTaxIdentificationNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public InsuredPerson read(long id) {
        InsuredPerson insuredPerson = new InsuredPerson();
        String selectRequest = "SELECT " + INSURED_PERSON_SURNAME + ", " + INSURED_PERSON_NAME + ", " + INSURED_PERSON_PATRONIMIC + ", " + INSURED_PERSON_BIRTHDAY + ", " + INSURED_PERSON_INSURANCE_PRICE + ", " + INSURED_PERSON_TAX_IDENTIFICATION_NUMBER + " FROM " + INSURED_PERSON_TABLE_NAME + " WHERE " + INSURED_PERSON_ID + "=?;";

        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectRequest)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                insuredPerson.setLastName(resultSet.getString(INSURED_PERSON_SURNAME));
                insuredPerson.setFirstName(resultSet.getString(INSURED_PERSON_NAME));
                insuredPerson.setPatronimic(resultSet.getString(INSURED_PERSON_PATRONIMIC));
                insuredPerson.setBirthDay(resultSet.getDate(INSURED_PERSON_BIRTHDAY).toLocalDate());
                insuredPerson.setInsurancePrice(resultSet.getInt(INSURED_PERSON_INSURANCE_PRICE));
                insuredPerson.setTaxIdentificationNumber(resultSet.getInt(INSURED_PERSON_TAX_IDENTIFICATION_NUMBER));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return insuredPerson;
    }

    @Override
    public void update(InsuredPerson entity) {
        String updateRequst = "Update " + INSURED_PERSON_TABLE_NAME + " SET " + INSURED_PERSON_SURNAME + "=?, " + INSURED_PERSON_NAME + "=?, " + INSURED_PERSON_PATRONIMIC + "=?, " + INSURED_PERSON_BIRTHDAY + "=?, " + INSURED_PERSON_INSURANCE_PRICE + "=?, " + INSURED_PERSON_TAX_IDENTIFICATION_NUMBER + "=? WHERE " + INSURED_PERSON_ID + "=?;";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateRequst)) {
            preparedStatement.setString(1, entity.getLastName());
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getPatronimic());
            preparedStatement.setDate(4, Date.valueOf(entity.getBirthDay()));
            preparedStatement.setInt(5, entity.getInsurancePrice());
            preparedStatement.setInt(6, entity.getTaxIdentificationNumber());
            preparedStatement.setInt(7, entity.getInsuredPersonId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        String deleteRequest = "DELETE FROM " + INSURED_PERSON_TABLE_NAME + " WHERE " + INSURED_PERSON_ID + "=?;";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteRequest)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public int getLastInsertedId() {
        return super.getLastInsertedId(INSURED_PERSON_TABLE_NAME);
    }
}
