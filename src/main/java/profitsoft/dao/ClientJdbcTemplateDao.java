package profitsoft.dao;


import profitsoft.data.Client;
import profitsoft.dict.Type;

public class ClientJdbcTemplateDao extends DBJdbcTemplateConnection implements IDao<Client> {
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
        jdbcTemplate.update(insertRequest,entity.getLastName(),entity.getFirstName(),entity.getPatronimic(),entity.getCompanyName(),entity.getType().toString(),entity.getAdress());
    }

    @Override
    public Client read(long id) {
        String selectRequest = "SELECT " + CLIENT_SURNAME + ", " + CLIENT_NAME + ", " + CLIENT_PATRONIMIC + ", " + COMPANY_NAME + ", " + CLIENT_TYPE + ", " + CLIENT_ADRESS + " FROM " + CLIENT_TABLE_NAME + " WHERE " + CLIENT_ID + "=?;";
        return jdbcTemplate.queryForObject(selectRequest,new Object[]{id}, (resultSet, i) -> {
            Client client=new Client();
            client.setLastName(resultSet.getString(CLIENT_SURNAME));
            client.setFirstName(resultSet.getString(CLIENT_NAME));
            client.setPatronimic(resultSet.getString(CLIENT_PATRONIMIC));
            client.setCompanyName(resultSet.getString(COMPANY_NAME));
            client.setType(Type.valueOf(resultSet.getString(CLIENT_TYPE)));
            client.setAdress(resultSet.getString(CLIENT_ADRESS));
            return client;
        });
    }


    @Override
    public void update(Client entity) {
        String updateRequst = "Update " + CLIENT_TABLE_NAME + " SET " + CLIENT_SURNAME + "=?, " + CLIENT_NAME + "=?, " + CLIENT_PATRONIMIC + "=?, " + COMPANY_NAME + "=?, " + CLIENT_TYPE + "=?, " + CLIENT_ADRESS + "=? WHERE " + CLIENT_ID + "=?;";
        jdbcTemplate.update(updateRequst,entity.getLastName(),entity.getFirstName(),entity.getPatronimic(),entity.getCompanyName(),entity.getType().toString(),entity.getAdress(),entity.getClientId());
    }

    @Override
    public void delete(long id) {
        String deleteRequest = "DELETE FROM " + CLIENT_TABLE_NAME + " WHERE " + CLIENT_ID + "=?;";
        jdbcTemplate.update(deleteRequest,id);
    }

    public int getLastInsertedId() {
        return super.getLastInsertedId(CLIENT_TABLE_NAME);
    }
}
