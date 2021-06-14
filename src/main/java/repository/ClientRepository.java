package repository;

import domain.Client;
import domain.validators.ClientValidator;
import domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class ClientRepository implements CrudRepository<String, Client> {

    SessionFactory sessionFactory;
    ClientValidator clientValidator;

    public ClientRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
        clientValidator = new ClientValidator();
    }

    @Override
    public Client save(Client entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        try {
            clientValidator.validate(entity);
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
    public Client delete(String numeUtilizator) {
        if (numeUtilizator == null)
            throw new IllegalArgumentException();
        Client client = findOne(numeUtilizator);
        if (client == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(client);
            session.getTransaction().commit();
            return client;
        }
    }

    @Override
    public Client update(Client entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(entity.getNumeUtilizator()) == null)
            return entity;
        try {
            clientValidator.validate(entity);
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
    public Client findOne(String numeUtilizator) {
        if (numeUtilizator == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Client> result = session.createQuery("select a from Client a where numeUtilizator=:numeUtilizator")
                    .setParameter("numeUtilizator", numeUtilizator)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    public Client findOneByEmail(String email) {
        if (email == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Client> result = session.createQuery("select a from Client a where email=:email")
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
    public List<Client> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Client> result = session.createQuery("select a from Client a").list();
            session.getTransaction().commit();
            return result;
        }
    }
}
