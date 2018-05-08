package com.agh.givealift.service;


import com.agh.givealift.Configuration;
import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.CityInfo;
import com.agh.givealift.repository.CityRepository;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final COD cod = CODFactory.get();

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> list() {
        return cityRepository.findAll();
    }

    public void saveAll(List<City> cities) {
        cityRepository.saveAll(cities);
    }


    public void removeAll() {
        cityRepository.deleteAll();
    }

    public Optional<City> get(Long cityId) {
        return cityRepository.findById(cityId);
    }

    public List<City> search(String name, int limit) {

        List<City> result = cityRepository.findCities(name, PageRequest.of(0, limit));
        cod.i("CITY search", result);
        return result;
    }

    public List<City> generate(int limit) {

        List<List<Object>> v = Configuration.TMP_CITIES_LIST;

        List<City> cities = new ArrayList<>();
        for (int i = 0; (i < v.size() && i < limit); i++) {
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
        removeAll();
        saveAll(cities);
        return cities;
    }


}
