package com.agh.givealift.repository;

import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Route;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

//    List<City> findByNameStartingWithIgnoreCase(String countryName);

    @Query("select c from City c join c.cityInfo ci WHERE UPPER(c.name) LIKE CONCAT(UPPER(:countryName),'%') ORDER BY ci.population DESC ")
    List<City> findCities(@Param(value = "countryName") String countryName, Pageable pageable);


}

