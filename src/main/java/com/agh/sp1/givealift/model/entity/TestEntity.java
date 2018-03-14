package com.agh.sp1.givealift.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestEntity {
    private long id;

    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
