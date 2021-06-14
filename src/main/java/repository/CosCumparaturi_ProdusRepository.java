package repository;

import domain.CosCumparaturi_Produs;
import domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class CosCumparaturi_ProdusRepository implements CrudRepository<String, CosCumparaturi_Produs> {
    SessionFactory sessionFactory;

    public CosCumparaturi_ProdusRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
    }

    @Override
    public CosCumparaturi_Produs save(CosCumparaturi_Produs entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();

        CosCumparaturi_Produs elementReturned = findOne(entity.getId_CosCumparaturi_Produs());
        if (elementReturned != null) {
            entity.setCantitate(elementReturned.getCantitate() + 1);
            update(entity);
        } else {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.save(entity);
                session.getTransaction().commit();
                return null;
            }
        }
        return entity;
    }

    @Override
    public CosCumparaturi_Produs delete(String id_CosCumparaturi_Produs) {
        if (id_CosCumparaturi_Produs == null)
            throw new IllegalArgumentException();
        CosCumparaturi_Produs c_p = findOne(id_CosCumparaturi_Produs);
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
    public CosCumparaturi_Produs update(CosCumparaturi_Produs entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(String.valueOf(entity.getId_CosCumparaturi_Produs())) == null)
            return entity;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            return null;
        }
    }

    @Override
    public CosCumparaturi_Produs findOne(String id_CosCumparaturi_Produs) {
        if (id_CosCumparaturi_Produs == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<CosCumparaturi_Produs> result = session.createQuery("select a from CosCumparaturi_Produs a where id_CosCumparaturi_Produs=:id_CosCumparaturi_Produs")
                    .setParameter("id_CosCumparaturi_Produs", id_CosCumparaturi_Produs)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<CosCumparaturi_Produs> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<CosCumparaturi_Produs> result = session.createQuery("select a from CosCumparaturi_Produs a").list();
            session.getTransaction().commit();
            return result;
        }
    }
}
