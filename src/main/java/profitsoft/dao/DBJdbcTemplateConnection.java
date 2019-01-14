package profitsoft.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class DBJdbcTemplateConnection {

@Autowired
protected JdbcTemplate jdbcTemplate=(JdbcTemplate)((new FileSystemXmlApplicationContext("classpath:database-config.xml")).getBean("jdbcTemplate"));
    @Bean
    public int getLastInsertedId(String tableName){
        String selectRequest="SELECT `AUTO_INCREMENT`-1 FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA ='insurance' " +
                "AND   TABLE_NAME   ='"+tableName+"';";
        return jdbcTemplate.queryForObject(selectRequest, (resultSet, i) -> resultSet.getInt(1));
    }
}
