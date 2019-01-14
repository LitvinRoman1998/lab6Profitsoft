package profitsoft.dao;

import org.junit.Assert;
import org.junit.Test;
import profitsoft.data.Client;
import profitsoft.dict.Type;

public class ClientJdbcDaoTest {
    @Test
    public void readTest(){
        Client clientI = new Client();
        clientI.setType(Type.INDIVIDUAL);
        clientI.setLastName("Litvin");
        clientI.setFirstName("Roman");
        clientI.setPatronimic("Mihailovich");
        clientI.setAdress("Pushkinska Street, 23, 18");
        ClientJdbcDao clientJdbcDao =new ClientJdbcDao();
        clientJdbcDao.create(clientI);
        Client clientI2 = new Client();
        int id= clientJdbcDao.getLastInsertedId();
        clientI2= clientJdbcDao.read(id);
        Assert.assertEquals(clientI,clientI2);

   }
   @Test
    public void updateTest(){
       Client clientE = new Client();
       clientE.setType(Type.ENTITY);
       clientE.setCompanyName("abc");
       clientE.setAdress("Pushkin Street, 23");
       ClientJdbcDao clientJdbcDao =new ClientJdbcDao();
       clientJdbcDao.create(clientE);
       Client clientE2 = new Client();
       int id= clientJdbcDao.getLastInsertedId();
       clientE.setClientId(id);
       clientE.setAdress("Pushkin Street, 38");
       clientJdbcDao.update(clientE);
       clientE2= clientJdbcDao.read(id);
       Assert.assertEquals(clientE,clientE2);
   }
}
