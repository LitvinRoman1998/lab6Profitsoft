package profitsoft.dao;

import org.springframework.transaction.annotation.Transactional;
import profitsoft.data.Contract;
import profitsoft.data.InsuredPerson;

public class ContractHibernateDao extends DBHibernateConnection implements IDao<Contract> {
    @Override
    @Transactional
    public void create(Contract entity) {
        session.beginTransaction();
        session.saveOrUpdate(entity.getClient());
        for (InsuredPerson insuredPerson: entity.getInsuredPeople()) {
            session.save(insuredPerson);
        }
        session.save(entity);
        session.getTransaction().commit();
    }

    @Override
    @Transactional
    public Contract read(long id) {
        return session.load(Contract.class,(int)id);
    }

    @Override
    @Transactional
    public void update(Contract entity) {
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
