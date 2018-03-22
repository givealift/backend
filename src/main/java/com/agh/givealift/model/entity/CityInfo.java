package com.agh.givealift.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CityInfo {
    private long cityInfoId;
    private long population;
    private long citySize;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getCityInfoId() {
        return cityInfoId;
    }

    public void setCityInfoId(long cityInfoId) {
        this.cityInfoId = cityInfoId;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public long getCitySize() {
        return citySize;
    }

    public void setCitySize(long citySize) {
        this.citySize = citySize;
    }
}
