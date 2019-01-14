package profitsoft.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class DBHibernateConnection {
    SessionFactory sessionFactory = ((SessionFactory) ((new FileSystemXmlApplicationContext("classpath:spring-db.xml")).getBean("sessionFactory")));
    Session session = sessionFactory.openSession();
}
