package repository;

import domain.CosCumparaturi;
import domain.validators.CosCumparaturiValidator;
import domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class CosCumparaturiRepository implements CrudRepository<String, CosCumparaturi> {

    SessionFactory sessionFactory;
    CosCumparaturiValidator cosCumparaturiValidator;

    public CosCumparaturiRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
        cosCumparaturiValidator = new CosCumparaturiValidator();
    }

    @Override
    public CosCumparaturi save(CosCumparaturi entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        try {
            cosCumparaturiValidator.validate(entity);
        } catch (Validator.ValidationException exception) {
            throw new Validator.ValidationException(exception.getMessage());
        }
        if (findOne(String.valueOf(entity.getIdCosCumparaturi())) != null)
            return entity;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return null;
        }
    }

    @Override
    public CosCumparaturi delete(String idCosCumparaturi) {
        if (idCosCumparaturi == null)
            throw new IllegalArgumentException();
        CosCumparaturi cosCumparaturi = findOne(idCosCumparaturi);
        if (cosCumparaturi == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(cosCumparaturi);
            session.getTransaction().commit();
            return cosCumparaturi;
        }
    }

    @Override
    public CosCumparaturi update(CosCumparaturi entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(String.valueOf(entity.getIdCosCumparaturi())) == null)
            return entity;
        try {
            cosCumparaturiValidator.validate(entity);
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
    public CosCumparaturi findOne(String idCosCumparaturi) {
        if (idCosCumparaturi == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<CosCumparaturi> result = session.createQuery("select a from CosCumparaturi a where idCosCumparaturi=:idCosCumparaturi")
                    .setParameter("idCosCumparaturi", Integer.parseInt(idCosCumparaturi))
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<CosCumparaturi> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<CosCumparaturi> result = session.createQuery("select a from CosCumparaturi a").list();
            session.getTransaction().commit();
            return result;
        }
    }

    public CosCumparaturi findOneUtilizator(String numeUtilizator) {
        if (numeUtilizator == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<CosCumparaturi> result = session.createQuery("select a from CosCumparaturi a where numeUtilizatorClient=:numeUtilizator")
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
