package profitsoft.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import profitsoft.data.Contract;
import profitsoft.data.InsuredPerson;

import java.util.*;

@Component
public class ContractJdbcTemplateDao extends DBJdbcTemplateConnection implements IDao<Contract> {
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


    @Bean
    @Override
    public void create(Contract entity) {
        ClientJdbcTemplateDao clientJdbcTemplateDao = new ClientJdbcTemplateDao();
        InsuredPersonJdbcTemplateDao insuredPersonJdbcTemplateDao = new InsuredPersonJdbcTemplateDao();
        clientJdbcTemplateDao.create(entity.getClient());
        int clientId = clientJdbcTemplateDao.getLastInsertedId();
        List<Integer> insuredPeopleIdList = new ArrayList<>();
        String insertRequest = "INSERT INTO " + CONTRACT_TABLE_NAME + "(" + CONTRACT_CONCLUSION_DATE + ", " + CONTRACT_START_DATE + ", " + CONTRACT_END_DATE + ", " + CONTRACT_FK_CLIENT_ID + ") VALUES(?,?,?,?);";
        jdbcTemplate.update(insertRequest, entity.getConclusionDate().plusDays(1), entity.getStartDate().plusDays(1), entity.getEndDate().plusDays(1), clientId);

        lastInsertedContract = getLastInsertedId();

        for (InsuredPerson insuredPerson : entity.getInsuredPeople()) {
            insuredPersonJdbcTemplateDao.create(insuredPerson);
            insuredPeopleIdList.add(insuredPersonJdbcTemplateDao.getLastInsertedId());
        }
        insertRequest = "INSERT INTO " + CONTRACT_HAS_INSUREDPERSON_TABLE_NAME + "(" + CONTRACT_HAS_INSUREDPERSON_INSUREDPERSON_NUM + ", " + CONTRACT_HAS_INSUREDPERSON_CONTRACT_NUM + ") VALUES(?,?);";
        for (Integer insuredPersonId : insuredPeopleIdList) {
            jdbcTemplate.update(insertRequest, insuredPersonId, lastInsertedContract);
        }
    }
    @Bean
    @Override
    public Contract read(long id) {
        ClientJdbcTemplateDao clientJdbcTemplateDao = new ClientJdbcTemplateDao();
        InsuredPersonJdbcTemplateDao insuredPersonJdbcTemplateDao = new InsuredPersonJdbcTemplateDao();
        HashSet<InsuredPerson> insuredPeople = new LinkedHashSet<>();
        String selectRequest = "SELECT " + CONTRACT_CONCLUSION_DATE + ", " + CONTRACT_START_DATE + ", " + CONTRACT_END_DATE + ", " + CONTRACT_FK_CLIENT_ID + " FROM " + CONTRACT_TABLE_NAME + " WHERE " + CONTRACT_ID + "=" + id + ";";
        Contract contract = jdbcTemplate.queryForObject(selectRequest,(resultSet, i) -> {
            Contract contract1 = new Contract();
            contract1.setConclusionDate(resultSet.getDate(CONTRACT_CONCLUSION_DATE).toLocalDate());
            contract1.setStartDate(resultSet.getDate(CONTRACT_START_DATE).toLocalDate());
            contract1.setEndDate(resultSet.getDate(CONTRACT_END_DATE).toLocalDate());
            contract1.setClient(clientJdbcTemplateDao.read(resultSet.getInt(CONTRACT_FK_CLIENT_ID)));
            return contract1;
        });
        selectRequest = "SELECT " + CONTRACT_HAS_INSUREDPERSON_INSUREDPERSON_NUM + " FROM " + CONTRACT_HAS_INSUREDPERSON_TABLE_NAME + " WHERE " + CONTRACT_HAS_INSUREDPERSON_CONTRACT_NUM + "=?;";
        insuredPeople.addAll(jdbcTemplate.query(selectRequest, new Object[]{id}, (resultSet, i) -> insuredPersonJdbcTemplateDao.read(resultSet.getInt(CONTRACT_HAS_INSUREDPERSON_INSUREDPERSON_NUM))));
        contract.setInsuredPeople(insuredPeople);
        return contract;
    }
    @Bean
    @Override
    public void update(Contract entity) {
        ClientJdbcTemplateDao clientJdbcTemplateDao = new ClientJdbcTemplateDao();
        InsuredPersonJdbcTemplateDao insuredPersonJdbcTemplateDao = new InsuredPersonJdbcTemplateDao();

        String updateRequst = "Update " + CONTRACT_TABLE_NAME + " SET " + CONTRACT_CONCLUSION_DATE + "=?, " + CONTRACT_START_DATE + "=?, " + CONTRACT_END_DATE + "=?, " + " WHERE " + CONTRACT_ID + "=?;";
        jdbcTemplate.update(updateRequst, entity.getConclusionDate().plusDays(1), entity.getStartDate().plusDays(1), entity.getEndDate().plusDays(1), entity.getId());

        clientJdbcTemplateDao.update(entity.getClient());

        for (InsuredPerson insuredPerson : entity.getInsuredPeople()) {
            insuredPersonJdbcTemplateDao.update(insuredPerson);
        }

    }
    @Bean
    @Override
    public void delete(long id) {
        String deleteRequest = "DELETE FROM " + CONTRACT_HAS_INSUREDPERSON_TABLE_NAME + " WHERE " + CONTRACT_HAS_INSUREDPERSON_CONTRACT_NUM + "=?;";
        jdbcTemplate.update(deleteRequest,id);
        deleteRequest = "DELETE FROM " + CONTRACT_TABLE_NAME + " WHERE " + CONTRACT_ID + "=" + id + ";";
        jdbcTemplate.update(deleteRequest,id);
    }
    @Bean
    public int getLastInsertedId() {
        return super.getLastInsertedId(CONTRACT_TABLE_NAME);
    }
}
