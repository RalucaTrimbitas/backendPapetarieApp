package repository;

import domain.Produs;
import domain.validators.ProdusValidator;
import domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class ProdusRepository implements CrudRepository<String, Produs> {

    SessionFactory sessionFactory;
    ProdusValidator produsValidator;

    public ProdusRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
        produsValidator = new ProdusValidator();
    }

    @Override
    public Produs save(Produs entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        try {
            produsValidator.validate(entity);
        } catch (Validator.ValidationException exception) {
            throw new Validator.ValidationException(exception.getMessage());
        }
        if (findOne(entity.getCodDeBare()) != null)
            return entity;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return null;
        }
    }

    @Override
    public Produs delete(String codDeBare) {
        if (codDeBare == null)
            throw new IllegalArgumentException();
        Produs produs = findOne(codDeBare);
        if (produs == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(produs);
            session.getTransaction().commit();
            return produs;
        }
    }

    @Override
    public Produs update(Produs entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(entity.getCodDeBare()) == null)
            return entity;
        try {
            produsValidator.validate(entity);
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.update(entity);
                session.getTransaction().commit();
                return null;
            }
        } catch (Validator.ValidationException e) {
            throw new Validator.ValidationException(e.getMessage());
        }
    }

    @Override
    public Produs findOne(String codDeBare) {
        if (codDeBare == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Produs> result = session.createQuery("select a from Produs a where codDeBare=:codDeBare")
                    .setParameter("codDeBare", codDeBare)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<Produs> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Produs> result = session.createQuery("select a from Produs a").list();
            session.getTransaction().commit();
            return result;
        }
    }
}
