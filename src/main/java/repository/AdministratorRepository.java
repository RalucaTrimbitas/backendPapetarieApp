package repository;

import domain.Administrator;
import domain.validators.AdministratorValidator;
import domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class AdministratorRepository implements CrudRepository<String, Administrator> {

    SessionFactory sessionFactory;
    AdministratorValidator administratorValidator;

    public AdministratorRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
        administratorValidator = new AdministratorValidator();
    }

    @Override
    public Administrator save(Administrator entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        try {
            administratorValidator.validate(entity);
        } catch (Validator.ValidationException exception) {
            throw new Validator.ValidationException(exception.getMessage());
        }
        if (findOne(entity.getNumeUtilizator()) != null)
            return entity;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return null;
        }
    }

    @Override
    public Administrator delete(String numeUtilizator) {
        if (numeUtilizator == null)
            throw new IllegalArgumentException();
        Administrator administrator = findOne(numeUtilizator);
        if (administrator == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(administrator);
            session.getTransaction().commit();
            return administrator;
        }
    }

    @Override
    public Administrator update(Administrator entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(entity.getNumeUtilizator()) == null)
            return entity;
        try {
            administratorValidator.validate(entity);
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
    public Administrator findOne(String numeUtilizator) {
        if (numeUtilizator == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Administrator> result = session.createQuery("select a from Administrator a where numeUtilizator=:numeUtilizator")
                    .setParameter("numeUtilizator", numeUtilizator)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    public Administrator findOneByEmail(String email) {
        if (email == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Administrator> result = session.createQuery("select a from Administrator a where email=:email")
                    .setParameter("email", email)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<Administrator> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Administrator> result = session.createQuery("select a from Administrator a").list();
            session.getTransaction().commit();
            return result;
        }
    }
}
