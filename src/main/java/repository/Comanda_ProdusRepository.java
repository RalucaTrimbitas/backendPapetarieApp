package repository;

import domain.Comanda_Produs;
import domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class Comanda_ProdusRepository implements CrudRepository<String, Comanda_Produs> {
    SessionFactory sessionFactory;

    public Comanda_ProdusRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
    }

    @Override
    public Comanda_Produs save(Comanda_Produs entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(entity.getId_Comanda_Produs()) != null)
            return entity;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return null;
        }
    }

    @Override
    public Comanda_Produs delete(String id_Comanda_Produs) {
        if (id_Comanda_Produs == null)
            throw new IllegalArgumentException();
        Comanda_Produs c_p = findOne(id_Comanda_Produs);
        if (c_p == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(c_p);
            session.getTransaction().commit();
            return c_p;
        }
    }

    @Override
    public Comanda_Produs update(Comanda_Produs entity) throws Validator.ValidationException {
        return null;
    }

    @Override
    public Comanda_Produs findOne(String id_Comanda_Produs) {
        if (id_Comanda_Produs == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Comanda_Produs> result = session.createQuery("select a from Comanda_Produs a where id_Comanda_Produs=:id_Comanda_Produs")
                    .setParameter("id_Comanda_Produs", id_Comanda_Produs)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<Comanda_Produs> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Comanda_Produs> result = session.createQuery("select a from Comanda_Produs a").list();
            session.getTransaction().commit();
            return result;
        }
    }
}
