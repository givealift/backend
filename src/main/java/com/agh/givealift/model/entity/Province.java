package com.agh.givealift.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Province {
    //    private long provinceId;
    private String name;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    public long getProvinceId() {
//        return provinceId;
//    }
//
//    public void setProvinceId(long provinceId) {
//        this.provinceId = provinceId;
//    }

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
