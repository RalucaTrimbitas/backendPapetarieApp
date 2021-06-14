package repository;

import domain.Categorie;
import domain.validators.CategorieValidator;
import domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class CategorieRepository implements CrudRepository<String, Categorie> {

    SessionFactory sessionFactory;
    CategorieValidator categorieValidator;

    public CategorieRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
        categorieValidator = new CategorieValidator();
    }

    @Override
    public Categorie save(Categorie entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        try {
            categorieValidator.validate(entity);
        } catch (Validator.ValidationException exception) {
            throw new Validator.ValidationException(exception.getMessage());
        }
        if (findOne(entity.getIdCategorie()) != null)
            return entity;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return null;
        }
    }

    @Override
    public Categorie delete(String idCategorie) {
        if (idCategorie == null)
            throw new IllegalArgumentException();
        Categorie categorie = findOne(idCategorie);
        if (categorie == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(categorie);
            session.getTransaction().commit();
            return categorie;
        }
    }

    @Override
    public Categorie update(Categorie entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(entity.getIdCategorie()) == null)
            return entity;
        try {
            categorieValidator.validate(entity);
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
    public Categorie findOne(String idCategorie) {
        if (idCategorie == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Categorie> result = session.createQuery("select a from Categorie a where idCategorie=:idCategorie")
                    .setParameter("idCategorie", idCategorie)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<Categorie> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Categorie> result = session.createQuery("select a from Categorie a").list();
            session.getTransaction().commit();
            return result;
        }
    }
}
