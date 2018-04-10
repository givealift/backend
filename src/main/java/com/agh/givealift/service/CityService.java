package com.agh.givealift.service;


import com.agh.givealift.Configuration;
import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.CityInfo;
import com.agh.givealift.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

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

    public List<City> search(String name) {
        List<City> result = cityRepository.findByNameStartingWithIgnoreCase(name);
        result.forEach(r -> System.out.println("b: " + r.getName()));
        result.sort((o1, o2) -> o2.getCityInfo().getPopulation().compareTo(o1.getCityInfo().getPopulation()));
        result.forEach(r -> System.out.println("b: " + r.getName()));
        return result;
    }

    public void generate() {

        List<List<Object>> v = Configuration.TMP_CITIES_LIST;

        List<City> cities = new ArrayList<>();
        for (int i = 0; i < v.size(); i++) {
            List<Object> x = v.get(i);
            City c = new City();
            c.setName((String) x.get(0));
            c.setCountry((String) x.get(1));
            c.setProvince((String) x.get(2));
            CityInfo ci = new CityInfo();
            ci.setPopulation((Long) x.get(3));
            ci.setCitySize((Long) x.get(4));
            c.setCityInfo(ci);
            cities.add(c);
        }
        saveAll(cities);
    }


}
