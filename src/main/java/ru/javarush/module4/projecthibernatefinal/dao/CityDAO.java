package ru.javarush.module4.projecthibernatefinal.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.javarush.module4.projecthibernatefinal.entity.City;

import java.util.List;

public class CityDAO implements CityDAOImpl {
    private final SessionFactory sessionFactory;

    public CityDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<City> getItems(int offset, int limit) {
        Query<City> query = sessionFactory.getCurrentSession().createQuery("from City c", City.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }

    public int getTotalCount() {
        Query<Long> query = sessionFactory.getCurrentSession().createQuery("select count(c) from City c", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }
}
