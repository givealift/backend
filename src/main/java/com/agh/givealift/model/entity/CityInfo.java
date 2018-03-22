package com.agh.givealift.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CityInfo {
    private Long cityInfoId;
    private Long population;
    private Long citySize;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getCityInfoId() {
        return cityInfoId;
    }

    public void setCityInfoId(Long cityInfoId) {
        this.cityInfoId = cityInfoId;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Long getCitySize() {
        return citySize;
    }

    public void setCitySize(Long citySize) {
        this.citySize = citySize;
    }
}
