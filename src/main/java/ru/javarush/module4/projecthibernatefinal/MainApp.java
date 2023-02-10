package ru.javarush.module4.projecthibernatefinal;

import ru.javarush.module4.projecthibernatefinal.entity.City;
import ru.javarush.module4.projecthibernatefinal.redis.CityCountry;
import ru.javarush.module4.projecthibernatefinal.service.MainAppService;
import ru.javarush.module4.projecthibernatefinal.utils.RedisDataMapper;

import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        MainAppService mainAppService = new MainAppService();
        List<City> allCities = mainAppService.fetchAllCitiesData();
        List<CityCountry> preparedRedisData = RedisDataMapper.transformData(allCities);
        mainAppService.pushToRedisDB(preparedRedisData);
        mainAppService.shutdown();
    }
}
