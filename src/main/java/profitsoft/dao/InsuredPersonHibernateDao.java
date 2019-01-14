package profitsoft.dao;


import profitsoft.data.InsuredPerson;


public class InsuredPersonHibernateDao extends DBHibernateConnection implements IDao<InsuredPerson>{
    @Override

    public void create(InsuredPerson entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    @Override
    public InsuredPerson read(long id) {
        return session.load(InsuredPerson.class,(int)id);
    }

    @Override
    public void update(InsuredPerson entity) {
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    @Override
    public void delete(long id) {
        session.beginTransaction();
        session.delete(read(id));
        session.getTransaction().commit();
    }
}
