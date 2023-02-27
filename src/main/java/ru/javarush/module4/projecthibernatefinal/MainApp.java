package ru.javarush.module4.projecthibernatefinal;

import ru.javarush.module4.projecthibernatefinal.entity.City;
import ru.javarush.module4.projecthibernatefinal.redis.CityCountry;
import ru.javarush.module4.projecthibernatefinal.service.MainAppService;
import ru.javarush.module4.projecthibernatefinal.utils.RedisDataMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MainApp {
    public static void main(String[] args) {
        MainAppService mainAppService = new MainAppService();
        List<City> allCities = mainAppService.fetchAllCitiesData();
        List<CityCountry> preparedRedisData = RedisDataMapper.transformData(allCities);
        mainAppService.pushToRedisDB(preparedRedisData);

        mainAppService.getSessionFactory().getCurrentSession().close();

        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ids.add(ThreadLocalRandom.current().nextInt(allCities.size()));
        }

        long startRedisDB = System.currentTimeMillis();
        mainAppService.testRedisData(ids);
        long stopRedisDB = System.currentTimeMillis();

        long startMysqlDB = System.currentTimeMillis();
        mainAppService.testMySqlData(ids);
        long stopMysqlDB = System.currentTimeMillis();

        System.out.println("Data retrieval speed from databases:");
        System.out.printf("Redis DB:\t%d ms\n", (stopRedisDB - startRedisDB));
        System.out.printf("MySQL DB:\t%d ms\n", (stopMysqlDB - startMysqlDB));

        mainAppService.shutdown();
    }
}
