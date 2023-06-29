package ru.javarush.module4.projecthibernatefinal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@Entity
@Table(schema = "world", name = "country")
public class Country {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "code", length = 3)
    private String code;

    @Column(name = "code_2", length = 2)
    private String alternativeCode;

    @Column(name = "name", length = 52)
    private String name;

    @Column(name = "continent")
    @Enumerated(EnumType.ORDINAL)
    private Continent continent;

    @Column(name = "region", length = 26)
    private String region;

    @Column(name = "surface_area")
    private BigDecimal surfaceArea;

    @Column(name = "indep_year")
    private Short independenceYear;

    @Column(name = "population")
    private Integer population;

    @Column(name = "life_expectancy")
    private BigDecimal lifeExpectancy;

    @Column(name = "gnp")
    private BigDecimal gnp;

    @Column(name = "gnpo_id")
    private BigDecimal gnpoId;

    @Column(name = "local_name", length = 45)
    private String localName;

    @Column(name = "government_form", length = 45)
    private String governmentForm;

    @Column(name = "head_of_state", length = 60)
    private String headOfState;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capital")
    private City city;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Set<CountryLanguage> languages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAlternativeCode() {
        return alternativeCode;
    }

    public void setAlternativeCode(String alternativeCode) {
        this.alternativeCode = alternativeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public BigDecimal getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(BigDecimal surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public Short getIndependenceYear() {
        return independenceYear;
    }

    public void setIndependenceYear(Short independenceYear) {
        this.independenceYear = independenceYear;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public BigDecimal getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(BigDecimal lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public BigDecimal getGnp() {
        return gnp;
    }

    public void setGnp(BigDecimal GNP) {
        this.gnp = GNP;
    }

    public BigDecimal getGnpoId() {
        return gnpoId;
    }

    public void setGnpoId(BigDecimal GNPOId) {
        this.gnpoId = GNPOId;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getGovernmentForm() {
        return governmentForm;
    }

    public void setGovernmentForm(String governmentForm) {
        this.governmentForm = governmentForm;
    }

    public String getHeadOfState() {
        return headOfState;
    }

    public void setHeadOfState(String headOfState) {
        this.headOfState = headOfState;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<CountryLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<CountryLanguage> languages) {
        this.languages = languages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id.equals(country.id) &&
                Objects.equals(code, country.code) &&
                Objects.equals(alternativeCode, country.alternativeCode) &&
                Objects.equals(name, country.name) &&
                continent == country.continent &&
                Objects.equals(region, country.region) &&
                Objects.equals(surfaceArea, country.surfaceArea) &&
                Objects.equals(independenceYear, country.independenceYear) &&
                Objects.equals(population, country.population) &&
                Objects.equals(lifeExpectancy, country.lifeExpectancy) &&
                Objects.equals(gnp, country.gnp) &&
                Objects.equals(gnpoId, country.gnpoId) &&
                Objects.equals(localName, country.localName) &&
                Objects.equals(governmentForm, country.governmentForm) &&
                Objects.equals(headOfState, country.headOfState) &&
                Objects.equals(city, country.city) &&
                Objects.equals(languages, country.languages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, alternativeCode, name, continent, region, surfaceArea, independenceYear, population,
                lifeExpectancy, gnp, gnpoId, localName, governmentForm, headOfState, city, languages);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Country.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("code='" + code + "'")
                .add("alternativeCode='" + alternativeCode + "'")
                .add("name='" + name + "'")
                .add("continent=" + continent)
                .add("region='" + region + "'")
                .add("surfaceArea=" + surfaceArea)
                .add("independenceYear=" + independenceYear)
                .add("population=" + population)
                .add("lifeExpectancy=" + lifeExpectancy)
                .add("GNP=" + gnp)
                .add("GNPOId=" + gnpoId)
                .add("localName='" + localName + "'")
                .add("governmentForm='" + governmentForm + "'")
                .add("headOfState='" + headOfState + "'")
                .add("city=" + city)
                .add("languages=" + languages)
                .toString();
    }
}
