package profitsoft.dao;

import profitsoft.connection.ConnectionFactory;
import profitsoft.data.Client;
import profitsoft.dict.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientJdbcDao extends DBJdbcConnection implements IDao<Client> {
    private static final String CLIENT_TABLE_NAME = "client";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SURNAME = "surname";
    private static final String CLIENT_NAME = "name";
    private static final String CLIENT_PATRONIMIC = "patronimic";
    private static final String COMPANY_NAME = "company_name";
    private static final String CLIENT_TYPE = "client_type";
    private static final String CLIENT_ADRESS = "adress";

    @Override
    public void create(Client entity) {
        String insertRequest = "INSERT INTO " + CLIENT_TABLE_NAME + "(" + CLIENT_SURNAME + ", " + CLIENT_NAME + ", " + CLIENT_PATRONIMIC + ", " + COMPANY_NAME + ", " + CLIENT_TYPE + ", " + CLIENT_ADRESS + ") VALUES(?,?,?,?,?,?);";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertRequest)) {
            preparedStatement.setString(1, entity.getLastName());
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getPatronimic());
            preparedStatement.setString(4, entity.getCompanyName());
            preparedStatement.setString(5, entity.getType().toString());
            preparedStatement.setString(6, entity.getAdress());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public Client read(long id) {
        Client client = new Client();
        String selectRequest = "SELECT " + CLIENT_SURNAME + ", " + CLIENT_NAME + ", " + CLIENT_PATRONIMIC + ", " + COMPANY_NAME + ", " + CLIENT_TYPE + ", " + CLIENT_ADRESS + " FROM " + CLIENT_TABLE_NAME + " WHERE " + CLIENT_ID + "=?;";

        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectRequest)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client.setLastName(resultSet.getString(CLIENT_SURNAME));
                client.setFirstName(resultSet.getString(CLIENT_NAME));
                client.setPatronimic(resultSet.getString(CLIENT_PATRONIMIC));
                client.setCompanyName(resultSet.getString(COMPANY_NAME));
                client.setType(Type.valueOf(resultSet.getString(CLIENT_TYPE)));
                client.setAdress(resultSet.getString(CLIENT_ADRESS));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return client;
    }


    @Override
    public void update(Client entity) {
        String updateRequst = "Update " + CLIENT_TABLE_NAME + " SET " + CLIENT_SURNAME + "=?, " + CLIENT_NAME + "=?, " + CLIENT_PATRONIMIC + "=?, " + COMPANY_NAME + "=?, " + CLIENT_TYPE + "=?, " + CLIENT_ADRESS + "=? WHERE " + CLIENT_ID + "=?;";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateRequst)) {
            preparedStatement.setString(1, entity.getLastName());
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getPatronimic());
            preparedStatement.setString(4, entity.getCompanyName());
            preparedStatement.setString(5, entity.getType().toString());
            preparedStatement.setString(6, entity.getAdress());
            preparedStatement.setInt(7, entity.getClientId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        String deleteRequest = "DELETE FROM " + CLIENT_TABLE_NAME + " WHERE " + CLIENT_ID + "=?;";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteRequest)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public int getLastInsertedId() {
        return super.getLastInsertedId(CLIENT_TABLE_NAME);
    }
}
