package ru.javarush.module4.projecthibernatefinal.utils;

import ru.javarush.module4.projecthibernatefinal.entity.City;
import ru.javarush.module4.projecthibernatefinal.entity.Country;
import ru.javarush.module4.projecthibernatefinal.entity.CountryLanguage;
import ru.javarush.module4.projecthibernatefinal.redis.CityCountry;
import ru.javarush.module4.projecthibernatefinal.redis.Language;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisDataMapper {

    public static List<CityCountry> transformData(List<City> cities) {
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

            Set<Language> languages = getLanguagesFromCountry(country);
            cityCountry.setLanguages(languages);

            return cityCountry;
        }).collect(Collectors.toList());
    }

    private static Set<Language> getLanguagesFromCountry(Country country) {
        Set<CountryLanguage> countryLanguages = country.getLanguages();

        return countryLanguages.stream().map(cl -> {
            Language language = new Language();
            language.setLanguage(cl.getLanguage());
            language.setOfficial(cl.getOfficial());
            language.setPercentage(cl.getPercentage());
            return language;
        }).collect(Collectors.toSet());
    }
}
