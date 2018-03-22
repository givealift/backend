package com.agh.givealift.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Country {
    //    private long countryId;
    private String name;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    public long getCountryId() {
//        return countryId;
//    }
//
//    public void setCountryId(long countryId) {
//        this.countryId = countryId;
//    }

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
