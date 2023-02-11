package ru.javarush.module4.projecthibernatefinal.dao;

import ru.javarush.module4.projecthibernatefinal.entity.City;

import java.util.List;
import java.util.Optional;

public interface CityDAOImpl {

    Optional<City> getById(Integer id);
    List<City> getItems(int offset, int limit);

    int getTotalCount();
}
