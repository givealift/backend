package com.agh.givealift.model.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
public class City {
    private long cityId;
    private String name;
    private Country country;
    private Province province;
    private CityInfo cityInfo;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }
}
