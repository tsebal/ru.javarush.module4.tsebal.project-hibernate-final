package ru.javarush.module4.projecthibernatefinal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javarush.module4.projecthibernatefinal.dao.CityDAO;
import ru.javarush.module4.projecthibernatefinal.dao.CountryDAO;
import ru.javarush.module4.projecthibernatefinal.entity.City;
import ru.javarush.module4.projecthibernatefinal.entity.Country;
import ru.javarush.module4.projecthibernatefinal.entity.CountryLanguage;
import ru.javarush.module4.projecthibernatefinal.redis.CityCountry;
import ru.javarush.module4.projecthibernatefinal.utils.MainAppSessionFactory;
import ru.javarush.module4.projecthibernatefinal.utils.RedisClientProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;

public class MainAppService {

    private static final Logger logger = LoggerFactory.getLogger(MainAppService.class);

    private final ObjectMapper mapper;
    private final SessionFactory sessionFactory;
    private final RedisClient redisClient;

    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;

    public MainAppService() {
        sessionFactory = MainAppSessionFactory.getSessionFactory();
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);

        redisClient = RedisClientProvider.prepareRedisClient();
        mapper = new ObjectMapper();
    }

    public List<City> fetchAllCitiesData() {
        try (Session session = sessionFactory.getCurrentSession()) {
            List<City> allCities = new ArrayList<>();
            session.beginTransaction();

            List<Country> countries = countryDAO.findAll();
            int totalCount = cityDAO.getTotalCount();
            int step = 500;
            for (int i = 0; i < totalCount; i += step) {
                allCities.addAll(cityDAO.getItems(i, step));
            }

            session.getTransaction().commit();
            return allCities;
        }
    }

    public void pushToRedisDB(List<CityCountry> data) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (CityCountry cityCountry : data) {
                try {
                    sync.set(String.valueOf(cityCountry.getId()), mapper.writeValueAsString(cityCountry));
                } catch (JsonProcessingException e) {
                    logger.error("Something wrong with data writing to RedisDB: " + e.getMessage());
                }
            }
            logger.info("Data was written to Redis successfully.");
        }
    }

    public void testMySqlData(List<Integer> ids) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            for (Integer id : ids) {
                City city = cityDAO.getById(id).orElseThrow();
                Set<CountryLanguage> languages = city.getCountry().getLanguages();
            }
            session.getTransaction().commit();
        }
    }

    public void testRedisData(List<Integer> ids) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (Integer id : ids) {
                String value = sync.get(String.valueOf(id));
                try {
                    mapper.readValue(value, CityCountry.class);
                } catch (JsonProcessingException e) {
                    logger.error("Something wrong with data reading from RedisDB: " + e.getMessage());
                }
            }
            logger.info("Data from Redis was read successfully.");
        }
    }

    public void shutdown() {
        if (nonNull(sessionFactory)) {
            sessionFactory.close();
        }
        if (nonNull(redisClient)) {
            redisClient.shutdown();
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
