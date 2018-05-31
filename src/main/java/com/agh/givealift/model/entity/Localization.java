package com.agh.givealift.model.entity;

import com.agh.givealift.configuration.Configuration;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Localization {
    private Long localizationId;
    private City city;
    private String placeOfMeeting;
//    private Integer buildingNumber;
    private Date date;

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

    public String getPlaceOfMeeting() {
        return placeOfMeeting;
    }

    public void setPlaceOfMeeting(String placeOfMeeting) {
        this.placeOfMeeting = placeOfMeeting;
    }

//    public Integer getBuildingNumber() {
//        return buildingNumber;
//    }
//
//    public void setBuildingNumber(Integer buildingNumber) {
//        this.buildingNumber = buildingNumber;
//    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Configuration.DATA_PATTERN)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
