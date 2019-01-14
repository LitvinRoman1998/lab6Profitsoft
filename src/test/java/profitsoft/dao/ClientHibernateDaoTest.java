package profitsoft.dao;

import org.junit.Assert;
import org.junit.Test;
import profitsoft.data.Client;
import profitsoft.dict.Type;

public class ClientHibernateDaoTest {
    @Test
    public void readTest(){
        Client clientI = new Client();
        clientI.setType(Type.INDIVIDUAL);
        clientI.setLastName("Litvin");
        clientI.setFirstName("Roman");
        clientI.setPatronimic("Mihailovich");
        clientI.setAdress("Pushkinska Street, 23, 18");
        ClientHibernateDao clientHibernateDao =new ClientHibernateDao();
        clientHibernateDao.create(clientI);
        Client clientI2 = new Client();
        clientI2= clientHibernateDao.read(clientI.getClientId());
        System.out.println(clientI2.getAdress());
        Assert.assertEquals(clientI,clientI2);

    }
    @Test
    public void updateTest(){
        Client clientE = new Client();
        clientE.setType(Type.ENTITY);
        clientE.setCompanyName("abc");
        clientE.setAdress("Pushkin Street, 23");
        ClientHibernateDao clientHibernateDao =new ClientHibernateDao();
        clientHibernateDao.create(clientE);
        Client clientE2;
        clientE.setAdress("Pushkin Street, 38");
        clientHibernateDao.update(clientE);
        clientE2= clientHibernateDao.read(clientE.getClientId());
        Assert.assertEquals(clientE,clientE2);
    }

}
