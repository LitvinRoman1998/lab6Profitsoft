package profitsoft.dao;

import org.springframework.transaction.annotation.Transactional;
import profitsoft.data.Client;

public class ClientHibernateDao extends DBHibernateConnection implements IDao<Client>{
    @Override
    @Transactional
    public void create(Client entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    @Override
    @Transactional
    public Client read(long id) {
        return session.load(Client.class,(int)id);
    }

    @Override
    @Transactional
    public void update(Client entity) {
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    @Override
    @Transactional
    public void delete(long id) {
        session.beginTransaction();
        session.delete(read(id));
        session.getTransaction().commit();
    }
}
