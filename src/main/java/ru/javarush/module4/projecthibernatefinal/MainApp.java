package ru.javarush.module4.projecthibernatefinal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.javarush.module4.projecthibernatefinal.dao.CountryDAO;
import ru.javarush.module4.projecthibernatefinal.dao.CityDAO;
import ru.javarush.module4.projecthibernatefinal.domain.Country;
import ru.javarush.module4.projecthibernatefinal.domain.City;
import ru.javarush.module4.projecthibernatefinal.domain.CountryLanguage;
import ru.javarush.module4.projecthibernatefinal.redis.CityCountry;
import ru.javarush.module4.projecthibernatefinal.redis.Language;
import ru.javarush.module4.projecthibernatefinal.utils.MainAppSessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class MainApp {
    private final ObjectMapper mapper;
    private final SessionFactory sessionFactory;
    private final RedisClient redisClient;

    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;

    public MainApp() {
        sessionFactory = MainAppSessionFactory.getSessionFactory();
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);

        redisClient = prepareRedisClient();
        mapper = new ObjectMapper();
    }

    public static void main(String[] args) {
        MainApp mainApp = new MainApp();
        List<City> allCities = mainApp.fetchData();
        List<CityCountry> preparedData = mainApp.transformData(allCities);
        mainApp.pushToRedis(preparedData);
        mainApp.shutdown();
    }

    private List<City> fetchData() {
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

    private List<CityCountry> transformData(List<City> cities) {
        return cities.stream().map(city -> {
            CityCountry cityCountry = new CityCountry();
            cityCountry.setId(city.getId());
            cityCountry.setName(city.getName());
            cityCountry.setPopulation(city.getPopulation());
            cityCountry.setDistrict(city.getDistrict());

            Country country = city.getCountry();
            cityCountry.setAlternativeCountryCode(country.getAlternativeCode());
            cityCountry.setContinent(country.getContinent());
            cityCountry.setCountryCode(country.getCode());
            cityCountry.setCountryName(country.getName());
            cityCountry.setCountryPopulation(country.getPopulation());
            cityCountry.setCountryRegion(country.getRegion());
            cityCountry.setCountrySurfaceArea(country.getSurfaceArea());

            Set<CountryLanguage> countryLanguages = country.getLanguages();
            Set<Language> languages = countryLanguages.stream().map(cl -> {
                Language language = new Language();
                language.setLanguage(cl.getLanguage());
                language.setOfficial(cl.getOfficial());
                language.setPercentage(cl.getPercentage());
                return language;
            }).collect(Collectors.toSet());
            cityCountry.setLanguages(languages);

            return cityCountry;
        }).collect(Collectors.toList());
    }

    private void pushToRedis(List<CityCountry> data) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (CityCountry cityCountry : data) {
                try {
                    sync.set(String.valueOf(cityCountry.getId()), mapper.writeValueAsString(cityCountry));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private RedisClient prepareRedisClient() {
        RedisClient redisClient = RedisClient.create(RedisURI.create("localhost", 6379));
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            System.out.println("\nConnected to Redis\n");
        }
        return redisClient;
    }

    private void shutdown() {
        if (nonNull(sessionFactory)) {
            sessionFactory.close();
        }
        if (nonNull(redisClient)) {
            redisClient.shutdown();
        }
    }
}
