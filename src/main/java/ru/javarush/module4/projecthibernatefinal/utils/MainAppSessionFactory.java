package ru.javarush.module4.projecthibernatefinal.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import ru.javarush.module4.projecthibernatefinal.domain.City;
import ru.javarush.module4.projecthibernatefinal.domain.Country;
import ru.javarush.module4.projecthibernatefinal.domain.CountryLanguage;

import java.util.Properties;

public class MainAppSessionFactory {
    private static MainAppSessionFactory instance;
    private final SessionFactory sessionFactory;

    private MainAppSessionFactory() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/world");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        properties.put(Environment.STATEMENT_BATCH_SIZE, "100");

        this.sessionFactory = new Configuration()
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(CountryLanguage.class)
                .addProperties(properties)
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if (instance == null) {
            instance = new MainAppSessionFactory();
        }
        return instance.sessionFactory;
    }
}
