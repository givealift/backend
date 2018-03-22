package com.agh.givealift.service;


import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public List<City> list() {
        return cityRepository.findAll();
    }

    public void saveAll(List<City> cities) {
        for (City c : cities) {
            cityRepository.save(c);
            System.out.println("saved City: " + c.getName());
        }
    }


    public City getOrCreate(City city) {
        if (city.getCityId() != null) {
            System.out.println(city.getCityId() + " n: " +
                    city.getName() + " c " +
                    city.getCountry() + " p " +
                    city.getProvince() + " ci: " +
                    city.getCityInfo());
            city = cityRepository.findById(city.getCityId()).orElse(city);
            System.out.println(city.getCityId() + " n: " +
                    city.getName() + " c " +
                    city.getCountry() + " p " +
                    city.getProvince() + " ci: " +
                    city.getCityInfo());
        }
        return city;
    }

}
