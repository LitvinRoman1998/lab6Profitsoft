package profitsoft.dao;

import profitsoft.connection.ConnectionFactory;
import profitsoft.data.Contract;
import profitsoft.data.InsuredPerson;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class ContractJdbcDao extends DBJdbcConnection implements IDao<Contract> {
    private static final String CONTRACT_TABLE_NAME = "contract";
    private static final String CONTRACT_HAS_INSUREDPERSON_TABLE_NAME = "contract_has_insuredperson";
    private static final String CONTRACT_ID = "contract_id";
    private static final String CONTRACT_CONCLUSION_DATE = "conclusion_date";
    private static final String CONTRACT_START_DATE = "start_date";
    private static final String CONTRACT_END_DATE = "end_date";
    private static final String CONTRACT_FK_CLIENT_ID = "fk_client_id";
    private static final String CONTRACT_HAS_INSUREDPERSON_INSUREDPERSON_NUM = "insuredperson_num";
    private static final String CONTRACT_HAS_INSUREDPERSON_CONTRACT_NUM = "contract_num";
    public static int lastInsertedContract;


    @Override
    public void create(Contract entity) {
        ClientJdbcDao clientJdbcDao = new ClientJdbcDao();
        InsuredPersonJdbcDao insuredPersonJdbcDao = new InsuredPersonJdbcDao();
        clientJdbcDao.create(entity.getClient());
        int clientId = clientJdbcDao.getLastInsertedId();
        List<Integer> insuredPeopleIdList = new ArrayList<>();
        String insertRequest = "INSERT INTO " + CONTRACT_TABLE_NAME + "(" + CONTRACT_CONCLUSION_DATE + ", " + CONTRACT_START_DATE + ", " + CONTRACT_END_DATE + ", " + CONTRACT_FK_CLIENT_ID + ") VALUES(?,?,?,?);";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertRequest)) {
            preparedStatement.setDate(1, Date.valueOf(entity.getConclusionDate().plusDays(1)));
            preparedStatement.setDate(2, Date.valueOf(entity.getStartDate().plusDays(1)));
            preparedStatement.setDate(3, Date.valueOf(entity.getEndDate().plusDays(1)));
            preparedStatement.setInt(4, clientId);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        lastInsertedContract = getLastInsertedId();

        for (InsuredPerson insuredPerson : entity.getInsuredPeople()) {
            insuredPersonJdbcDao.create(insuredPerson);
            insuredPeopleIdList.add(insuredPersonJdbcDao.getLastInsertedId());
        }
        insertRequest = "INSERT INTO " + CONTRACT_HAS_INSUREDPERSON_TABLE_NAME + "(" + CONTRACT_HAS_INSUREDPERSON_INSUREDPERSON_NUM + ", " + CONTRACT_HAS_INSUREDPERSON_CONTRACT_NUM + ") VALUES(?,?);";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertRequest)) {
            preparedStatement.setInt(2, lastInsertedContract);
            for (Integer insuredPersonId : insuredPeopleIdList) {
                preparedStatement.setInt(1, insuredPersonId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public Contract read(long id) {
        Contract contract = new Contract();
        ClientJdbcDao clientJdbcDao = new ClientJdbcDao();
        InsuredPersonJdbcDao insuredPersonJdbcDao = new InsuredPersonJdbcDao();
        List<Integer> insuredPeopleIdList = new ArrayList<>();
        HashSet<InsuredPerson> insuredPeople = new LinkedHashSet<>();
        String selectRequest = "SELECT " + CONTRACT_CONCLUSION_DATE + ", " + CONTRACT_START_DATE + ", " + CONTRACT_END_DATE + ", " + CONTRACT_FK_CLIENT_ID + " FROM " + CONTRACT_TABLE_NAME + " WHERE " + CONTRACT_ID + "=" + id + ";";

        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectRequest)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                contract.setConclusionDate(resultSet.getDate(CONTRACT_CONCLUSION_DATE).toLocalDate());
                contract.setStartDate(resultSet.getDate(CONTRACT_START_DATE).toLocalDate());
                contract.setEndDate(resultSet.getDate(CONTRACT_END_DATE).toLocalDate());
                contract.setClient(clientJdbcDao.read(resultSet.getInt(CONTRACT_FK_CLIENT_ID)));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        selectRequest = "SELECT " + CONTRACT_HAS_INSUREDPERSON_INSUREDPERSON_NUM + " FROM " + CONTRACT_HAS_INSUREDPERSON_TABLE_NAME + " WHERE " + CONTRACT_HAS_INSUREDPERSON_CONTRACT_NUM + "=" + id + ";";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectRequest)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                insuredPeopleIdList.add(resultSet.getInt(CONTRACT_HAS_INSUREDPERSON_INSUREDPERSON_NUM));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        for (Integer insuredPersonId : insuredPeopleIdList) {
            insuredPeople.add(insuredPersonJdbcDao.read(insuredPersonId));
        }
        contract.setInsuredPeople(insuredPeople);
        return contract;
    }

    @Override
    public void update(Contract entity) {
        ClientJdbcDao clientJdbcDao = new ClientJdbcDao();
        InsuredPersonJdbcDao insuredPersonJdbcDao = new InsuredPersonJdbcDao();

        String updateRequst = "Update " + CONTRACT_TABLE_NAME + " SET " + CONTRACT_CONCLUSION_DATE + "=?, " + CONTRACT_START_DATE + "=?, " + CONTRACT_END_DATE + "=?, " + " WHERE " + CONTRACT_ID+ "=?;";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateRequst)) {
            preparedStatement.setDate(1, Date.valueOf(entity.getConclusionDate()));
            preparedStatement.setDate(2, Date.valueOf(entity.getStartDate()));
            preparedStatement.setDate(3, Date.valueOf(entity.getEndDate()));
            preparedStatement.setLong(4,entity.getId());
            clientJdbcDao.update(entity.getClient());

            for(InsuredPerson insuredPerson:entity.getInsuredPeople()){
                insuredPersonJdbcDao.update(insuredPerson);
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        String deleteRequest = "DELETE FROM " + CONTRACT_HAS_INSUREDPERSON_TABLE_NAME + " WHERE " + CONTRACT_HAS_INSUREDPERSON_CONTRACT_NUM + "="+id+";";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteRequest)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        deleteRequest= "DELETE FROM " + CONTRACT_TABLE_NAME + " WHERE " + CONTRACT_ID + "="+id+";";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteRequest)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public int getLastInsertedId() {
        return super.getLastInsertedId(CONTRACT_TABLE_NAME);
    }
}
