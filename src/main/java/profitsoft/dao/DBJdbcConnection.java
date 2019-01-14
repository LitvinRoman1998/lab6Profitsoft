package profitsoft.dao;

import profitsoft.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBJdbcConnection {
    protected int getLastInsertedId(String tableName){
        String selectRequest="select last_insert_id() from "+tableName;
        int lastInsertedId=0;
        try(Connection connection= ConnectionFactory.getMySQLConnection();
            PreparedStatement preparedStatement =connection.prepareStatement(selectRequest)){
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                lastInsertedId=resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error: "+ e.getMessage());
        }
        return lastInsertedId;
    }
}
