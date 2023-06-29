package ru.javarush.module4.projecthibernatefinal.dao;

import ru.javarush.module4.projecthibernatefinal.entity.Country;

import java.util.List;

public interface CountryDao {
    List<Country> findAll();
}
