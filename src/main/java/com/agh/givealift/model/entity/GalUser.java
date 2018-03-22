package com.agh.givealift.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GalUser {
    private Long galUserId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getGalUserId() {
        return galUserId;
    }

    public void setGalUserId(Long galUserId) {
        this.galUserId = galUserId;
    }
}
