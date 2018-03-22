package com.agh.givealift.repository;

import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, String> {
    List<City> findByCountryName(String countryName);

    List<City> findByProvinceName(String provinceName);
}
