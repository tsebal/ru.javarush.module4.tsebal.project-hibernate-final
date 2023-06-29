package ru.javarush.module4.projecthibernatefinal.redis;

import ru.javarush.module4.projecthibernatefinal.entity.Continent;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public class CityCountry {
    private Integer id;

    private String name;

    private String district;

    private Integer population;

    private String countryCode;

    private String alternativeCountryCode;

    private String countryName;

    private Continent continent;

    private String countryRegion;

    private BigDecimal countrySurfaceArea;

    private Integer countryPopulation;

    private Set<Language> languages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAlternativeCountryCode() {
        return alternativeCountryCode;
    }

    public void setAlternativeCountryCode(String alternativeCountryCode) {
        this.alternativeCountryCode = alternativeCountryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public String getCountryRegion() {
        return countryRegion;
    }

    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }

    public BigDecimal getCountrySurfaceArea() {
        return countrySurfaceArea;
    }

    public void setCountrySurfaceArea(BigDecimal countrySurfaceArea) {
        this.countrySurfaceArea = countrySurfaceArea;
    }

    public Integer getCountryPopulation() {
        return countryPopulation;
    }

    public void setCountryPopulation(Integer countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityCountry that = (CityCountry) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(district, that.district) &&
                Objects.equals(population, that.population) &&
                Objects.equals(countryCode, that.countryCode) &&
                Objects.equals(alternativeCountryCode, that.alternativeCountryCode) &&
                Objects.equals(countryName, that.countryName) &&
                continent == that.continent &&
                Objects.equals(countryRegion, that.countryRegion) &&
                Objects.equals(countrySurfaceArea, that.countrySurfaceArea) &&
                Objects.equals(countryPopulation, that.countryPopulation) &&
                Objects.equals(languages, that.languages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, district, population, countryCode, alternativeCountryCode, countryName, continent, countryRegion, countrySurfaceArea, countryPopulation, languages);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CityCountry.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("district='" + district + "'")
                .add("population=" + population)
                .add("countryCode='" + countryCode + "'")
                .add("alternativeCountryCode='" + alternativeCountryCode + "'")
                .add("countryName='" + countryName + "'")
                .add("continent=" + continent)
                .add("countryRegion='" + countryRegion + "'")
                .add("countrySurfaceArea=" + countrySurfaceArea)
                .add("countryPopulation=" + countryPopulation)
                .add("languages=" + languages)
                .toString();
    }
}
