package profitsoft.connection;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class HibernateConnectionFactory {
    private static SessionFactory sessionFactory=(SessionFactory) ((new FileSystemXmlApplicationContext("classpath:spring-db.xml")).getBean("sessionFactory"));

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
