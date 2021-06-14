package repository;

import domain.Comanda;
import domain.validators.ComandaValidator;
import domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class ComandaRepository implements CrudRepository<String, Comanda> {
    SessionFactory sessionFactory;
    ComandaValidator comandaValidator;

    public ComandaRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
        comandaValidator = new ComandaValidator();
    }

    @Override
    public Comanda save(Comanda entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        try {
            comandaValidator.validate(entity);
        } catch (Validator.ValidationException exception) {
            throw new Validator.ValidationException(exception.getMessage());
        }
        if (findOne(String.valueOf(entity.getNumarComanda())) != null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public Comanda delete(String numarComanda) {
        if (numarComanda == null)
            throw new IllegalArgumentException();
        Comanda comanda = findOne(numarComanda);
        if (comanda == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(comanda);
            session.getTransaction().commit();
            return comanda;
        }
    }

    @Override
    public Comanda update(Comanda entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(String.valueOf(entity.getNumarComanda())) == null)
            return entity;
        try {
            comandaValidator.validate(entity);
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
    public Comanda findOne(String numarComanda) {
        if (numarComanda == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Comanda> result = session.createQuery("select a from Comanda a where numarComanda=:numarComanda")
                    .setParameter("numarComanda", Integer.parseInt(numarComanda))
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<Comanda> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Comanda> result = session.createQuery("select a from Comanda a").list();
            session.getTransaction().commit();
            return result;
        }
    }

    public Comanda findOneUtilizator(String numeUtilizator) {
        if (numeUtilizator == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Comanda> result = session.createQuery("select a from Comanda a where numeUtilizatorClient=:numeUtilizator")
                    .setParameter("numeUtilizator", numeUtilizator)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }
}
