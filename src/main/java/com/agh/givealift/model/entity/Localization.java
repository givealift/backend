package com.agh.givealift.model.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
public class Localization {
    private Long localizationId;
    private City city;
    private String street;
    private Integer buildingNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getLocalizationId() {
        return localizationId;
    }

    public void setLocalizationId(Long localizationId) {
        this.localizationId = localizationId;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(Integer buildingNumber) {
        this.buildingNumber = buildingNumber;
    }
}
