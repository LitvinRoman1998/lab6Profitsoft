package profitsoft.connection;


import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

public class ConnectionFactory {

    private static DataSource dataSource;

    static {
        Properties property = new Properties();
        try (Reader bufferedReader = new BufferedReader(new FileReader("src/main/resources/jdbcConnectionInformation.properties"))) {
            property.load(bufferedReader);
            dataSource = BasicDataSourceFactory.createDataSource(property);
        } catch (Exception exeption) {
            System.out.println("Error: " + exeption.getMessage());
        }
    }

    public static Connection getMySQLConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
