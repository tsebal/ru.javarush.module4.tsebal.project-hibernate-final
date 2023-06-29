package ru.javarush.module4.projecthibernatefinal.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.javarush.module4.projecthibernatefinal.entity.Country;

import java.util.List;

public class CountryHibernateDao implements CountryDao {
    private final SessionFactory sessionFactory;

    public CountryHibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Country> findAll() {
        Query<Country> query = sessionFactory.getCurrentSession().createQuery("from Country c join fetch c.languages", Country.class);
        return query.list();
    }
}
