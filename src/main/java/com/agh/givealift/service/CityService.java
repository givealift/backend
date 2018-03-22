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
        cities.forEach(this::save);
    }

    public void save(City city) {
        System.out.println(city.getName() +
                " co: " + city.getCountry().getName() +
                " p: " + city.getProvince().getName() +
                " ci: " + city.getCityInfo().getPopulation() +
                " | " + city.getCityInfo().getCitySize());
//        Optional<City> oldCityCountry = cityRepository.findByCountryName(city.getCountry().getName()).stream().findAny();
//        System.out.println(oldCityCountry.isPresent());
//        oldCityCountry.ifPresent(c -> city.setCountry(c.getCountry()));
        System.out.println("a 1");
//        Optional<City> oldCityProvince = cityRepository.findByProvinceName(city.getProvince().getName()).stream().findAny();
//        oldCityProvince.forEach(System.out::println);
//        System.out.println(oldCityProvince.isPresent());
//        oldCityProvince.ifPresent(c -> city.setProvince(c.getProvince()));
        System.out.println("a 2");
        cityRepository.save(city);
        System.out.println("saved");
    }


}
