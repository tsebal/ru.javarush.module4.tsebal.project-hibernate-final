package ru.javarush.module4.projecthibernatefinal.dao;

import ru.javarush.module4.projecthibernatefinal.entity.City;

import java.util.List;

public interface CityDAOImpl {

    List<City> getItems(int offset, int limit);

    int getTotalCount();
}
